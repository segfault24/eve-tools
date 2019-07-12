-- -----------------------------------------------------------------------------
-- Conventions used by this database:
--
-- tables and columns are named in camelCase
-- indices are named in the following convention:
--   px_<table>_<col>			  primary KEY (single column, sometimes on surrogate indices)
--   fx_<table>_<col1>_<col2>_...  foreign indices (constrain to other tables)
--   ux_<table>_<col1>_<col2>_...  unique indices (constraints on this table)
--   ix_<table>_<col1>_<col2>_...  alternative indices (faster operations)
-- additionally, indices will typically be defined in px, fx, ux, ix order
-- -----------------------------------------------------------------------------

USE `eve`;

-- -----------------------------------------------------------------------------

DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
	`regionId` int(11) NOT NULL,
	`regionName` varchar(100) NOT NULL,
	`description` text DEFAULT NULL,
	PRIMARY KEY (`regionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `constellation`;
CREATE TABLE `constellation` (
	`constellationId` int(11) NOT NULL,
	`constellationName` varchar(100) NOT NULL,
	`regionId` int(11) NOT NULL,
	PRIMARY KEY (`constellationId`),
	KEY `ix_constellation_regionId` (`regionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `solarSystem`;
CREATE TABLE `solarSystem` (
	`solarSystemId` int(11) NOT NULL,
	`solarSystemName` varchar(100) NOT NULL,
	`constellationId` int(11) NOT NULL,
	`regionId` int(11) NOT NULL,
	`x` double DEFAULT NULL,
	`y` double DEFAULT NULL,
	`z` double DEFAULT NULL,
	`security` double DEFAULT NULL,
	PRIMARY KEY (`solarSystemId`),
	KEY `ix_solarSystem_regionId` (`regionId`),
	KEY `ix_solarSystem_constellationId` (`constellationId`),
	KEY `ix_solarSystem_security` (`security`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `station`;
CREATE TABLE `station` (
	`stationId` bigint(20),
	`stationName` varchar(100) NOT NULL,
	`solarSystemId` int(11) NOT NULL,
	`constellationId` int(11) NOT NULL,
	`regionId` int(11) NOT NULL,
	PRIMARY KEY (`stationId`),
	KEY `ix_station_regionId` (`regionId`),
	KEY `ix_station_constellationId` (`constellationId`),
	KEY `ix_station_solarSystemId` (`solarSystemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `invType`;
CREATE TABLE `invType` (
	`typeId` int(11) NOT NULL,
	`groupId` int(11) DEFAULT NULL,
	`typeName` varchar(100) DEFAULT NULL,
	`description` text DEFAULT NULL,
	`mass` double DEFAULT NULL,
	`volume` double DEFAULT NULL,
	`published` tinyint(1) DEFAULT NULL,
	`marketGroupId` int(11) DEFAULT NULL,
	PRIMARY KEY (`typeId`),
	KEY `ix_invType_groupId` (`groupId`),
	KEY `ix_invType_marketGroupId` (`marketGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `marketGroup`;
CREATE TABLE `marketGroup` (
	`marketGroupId` int(11) NOT NULL,
	`parentGroupId` int(11) DEFAULT NULL,
	`marketGroupName` varchar(100) DEFAULT NULL,
	`description` varchar(3000) DEFAULT NULL,
	`hasTypes` tinyint(1) DEFAULT NULL,
	PRIMARY KEY (`marketGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `alliance`;
CREATE TABLE `alliance` (
	`allianceId` INT,
	`allianceName` VARCHAR(64),
	`ticker` VARCHAR(8),
	`dateFounded` DATE,
	`executorCorp` INT,
	PRIMARY KEY (`allianceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `corporation`;
CREATE TABLE `corporation` (
	`corpId` INT,
	`corpName` VARCHAR(64),
	`ticker` VARCHAR(8),
	`allianceId` INT,
	`ceo` INT,
	`creator` INT,
	`creationDate` DATE,
	`memberCount` INT,
	`taxRate` FLOAT,
	`url` VARCHAR(255),
	PRIMARY KEY (`corpId`),
	KEY `ix_corporation_allianceId` (`allianceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `character`;
CREATE TABLE `character` (
	`charId` INT,
	`charName` VARCHAR(64),
	`corpId` INT,
	`allianceId` INT,
	`birthday` DATE,
	PRIMARY KEY (`charId`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `structure`;
CREATE TABLE `structure` (
	`structId` BIGINT,
	`structName` VARCHAR(64),
	`corpId` INT,
	`systemId` INT NOT NULL,
	`regionId` INT NOT NULL,
	`typeId` INT NOT NULL,
	PRIMARY KEY (`structId`),
	KEY `ix_structure_regionId` (`regionId`),
	KEY `ix_structure_systemId` (`systemId`),
	KEY `ix_structure_corpId` (`corpId`)
) ENGINE=InnoDb DEFAULT CHARSET=utf8;

-- -----------------------------------------------------------------------------

DROP TABLE IF EXISTS `property`;
CREATE TABLE `property` (
	`id` INT AUTO_INCREMENT,
	`propertyName` VARCHAR(255) NULL,
	`propertyValue` VARCHAR(255) NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `ux_property_propertyName` (`propertyName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `kvStore`;
CREATE TABLE `kvStore`  (
	`id` INT AUTO_INCREMENT,
	`key` VARCHAR(255) NULL,
	`value` VARCHAR(255) NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `ux_kvStore_key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `dirtUser`;
CREATE TABLE `dirtUser` (
	`userId` INT AUTO_INCREMENT,
	`username` VARCHAR(64) NOT NULL,
	`name` VARCHAR(64),
	`hash` VARCHAR(255) NOT NULL,
	`dateCreated` TIMESTAMP NOT NULL,
	`lastLogin` TIMESTAMP NOT NULL,
	`admin` BOOLEAN NOT NULL DEFAULT FALSE,
	`disabled` BOOLEAN NOT NULL DEFAULT FALSE,
	PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `dirtList`;
CREATE TABLE `dirtList` (
	`listId` INT AUTO_INCREMENT,
	`userId` INT NOT NULL,
	`name` VARCHAR(64),
	`public` BOOLEAN NOT NULL DEFAULT 0,
	PRIMARY KEY (`listId`),
	FOREIGN KEY (`userId`)
		REFERENCES `dirtUser`(`userId`)
		ON DELETE CASCADE,
	KEY `ix_dirtList_userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `dirtListItem`;
CREATE TABLE `dirtListItem` (
	`itemEntryId` INT AUTO_INCREMENT,
	`listId` INT NOT NULL,
	`typeId` INT NOT NULL,
	`quantity` INT NOT NULL DEFAULT 1 CHECK (`quantity` >= 0),
	PRIMARY KEY (`itemEntryId`),
	FOREIGN KEY (`listId`)
		REFERENCES `dirtList` (`listId`)
		ON DELETE CASCADE,
	FOREIGN KEY (`typeId`)
		REFERENCES `invType` (`typeId`),
	UNIQUE KEY (`listId` , `typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `dirtApiAuth`;
CREATE TABLE `dirtApiAuth` (
	`keyId` INT AUTO_INCREMENT,
	`userId` INT NOT NULL,
	`charId` INT NOT NULL,
	`charName` VARCHAR(255),
	`charHash` VARCHAR(255),
	`token` VARCHAR(255),
	`expires` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`refresh` VARCHAR(255),
	PRIMARY KEY (`keyId`),
	FOREIGN KEY (`userId`)
		REFERENCES `dirtUser` (`userId`)
		ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `dirtStructAuth`;
CREATE TABLE `dirtStructAuth` (
	`dsaId` INT AUTO_INCREMENT,
	`keyId` INT NOT NULL,
	`structId` BIGINT NOT NULL,
	PRIMARY KEY (`dsaId`),
	UNIQUE KEY (`keyId`, `structId`),
	FOREIGN KEY (`keyId`)
		REFERENCES `dirtApiAuth` (`keyId`)
		ON DELETE CASCADE,
	FOREIGN KEY (`structId`)
		REFERENCES `structure` (`structId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `dirtAlert`;
CREATE TABLE `dirtAlert` (
	`alertId` INT AUTO_INCREMENT,
	`userId` INT NOT NULL,
	`alertType` INT NOT NULL,
	`enabled` BOOLEAN NOT NULL DEFAULT TRUE,
	`param1` VARCHAR(255),
	`param2` VARCHAR(255),
	`param3` VARCHAR(255),
	`param4` VARCHAR(255),
	`param5` VARCHAR(255),
	PRIMARY KEY (`alertId`),
	FOREIGN KEY (`userId`)
		REFERENCES `dirtUser`(`userId`)
		ON DELETE CASCADE,
	KEY `ix_dirtAlert_userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `dirtNotification`;
CREATE TABLE `dirtNotification` (
	`notifId` INT AUTO_INCREMENT,
	`time` TIMESTAMP NOT NULL,
	`userId` INT NOT NULL,
	`alertId` INT,
	`title` VARCHAR(64),
	`text` VARCHAR(1024),
	`acknowledged` BOOLEAN NOT NULL DEFAULT FALSE,
	PRIMARY KEY (`notifId`),
	FOREIGN KEY (`userId`)
		REFERENCES `dirtUser`(`userId`)
		ON DELETE CASCADE,
	FOREIGN KEY (`alertId`)
		REFERENCES `dirtAlert`(`alertId`)
		ON DELETE SET NULL,
	KEY `ix_dirtNotification_userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `fortchain`;
CREATE TABLE `fortchain` (
	`systemId` int(11) NOT NULL,
	`superDocking` BOOLEAN DEFAULT FALSE,
	PRIMARY KEY (`systemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `taskStatus`;
CREATE TABLE `taskStatus` (
	`taskName` VARCHAR(255),
	`lastRun` TIMESTAMP,
	`lastRunDuration` INT,
	PRIMARY KEY (`taskName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `apiReq`;
CREATE TABLE `apiReq` (
	`apiReqName` VARCHAR(64) NOT NULL,
	`etag` VARCHAR(64),
	PRIMARY KEY (`apiReqName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `walletJournal`;
CREATE TABLE `walletJournal` (
	`journalId` BIGINT,
	`charId` INT NOT NULL,
	`amount` DECIMAL(19,4),
	`balance` DECIMAL(19,4),
	`contextId` BIGINT,
	`contextIdType` VARCHAR(255),
	`date` TIMESTAMP NOT NULL,
	`description` VARCHAR(255),
	`firstPartyId` INT,
	`reason` VARCHAR(255),
	`refType` VARCHAR(255),
	`secondPartyId` INT,
	`tax` DECIMAL(19,4),
	`taxReceiverId` INT,
	PRIMARY KEY (`journalId`),
	KEY `ix_walletJournal_charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `walletTransaction`;
CREATE TABLE `walletTransaction` (
	`transactionId` BIGINT,
	`charId` INT NOT NULL,
	`clientId` INT,
	`date` TIMESTAMP NOT NULL,
	`isBuy` BOOLEAN NOT NULL,
	`isPersonal` BOOLEAN,
	`typeId` INT NOT NULL,
	`quantity` INT NOT NULL,
	`unitPrice` DECIMAL(19,4),
	`locationId` BIGINT NOT NULL,
	`journalRefId` BIGINT,
	PRIMARY KEY (`transactionId`),
	KEY `ix_walletTransaction_charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
	`contractId` INT NOT NULL,
	`issuerId` INT NOT NULL,
	`issuerCorpId` INT NOT NULL,
	`assigneeId` INT NOT NULL,
	`acceptorId` INT NOT NULL,
	`availability` VARCHAR(255),
	`dateIssued` TIMESTAMP NOT NULL,
	`dateExpired` TIMESTAMP NOT NULL,
	`status` VARCHAR(255),
	`type` VARCHAR(255),
	PRIMARY KEY (`contractId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------------------------------

DROP TABLE IF EXISTS `marketHistory`;
CREATE TABLE `marketHistory` (
	`histEntryId` BIGINT AUTO_INCREMENT,
	`typeId` INT NOT NULL,
	`regionId` INT NOT NULL,
	`date` DATE NOT NULL,
	`highest` DECIMAL(19, 4),
	`average` DECIMAL(19, 4),
	`lowest` DECIMAL(19, 4),
	`volume` BIGINT,
	`orderCount` BIGINT,
	PRIMARY KEY (`histEntryId`),
	UNIQUE KEY `ux_marketHistory_typeId_regionId_date` (`typeId`,`regionId`,`date`),
	KEY `ix_marketHistory_typeId_regionId` (`typeId`, `regionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `marketOrder`;
CREATE TABLE `marketOrder` (
	`orderEntryId` BIGINT AUTO_INCREMENT,
	`issued` TIMESTAMP,
	`range` VARCHAR(45),
	`isBuyOrder` BOOLEAN,
	`duration` INT,
	`orderId` BIGINT NOT NULL,
	`volumeRemain` INT,
	`minVolume` INT,
	`typeId` INT NOT NULL,
	`volumeTotal` INT,
	`locationId` BIGINT NOT NULL,
	`price` DECIMAL(19,4),
	`regionId` INT NOT NULL,
	`retrieved` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	-- source: 0=public, 1=structure, 2=character
	`source` INT NOT NULL DEFAULT 0,
	`charId` INT,
	PRIMARY KEY (`orderEntryId`),
	UNIQUE KEY `ux_marketOrder_orderId_retrieved` (`orderId`, `retrieved`),
	KEY `ix_marketOrder_typeId_regionId` (`typeId`, `regionId`),
	KEY `ix_marketOrder_locationId` (`locationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `insurancePrice`;
CREATE TABLE `insurancePrice` (
	`insuranceEntryId` BIGINT AUTO_INCREMENT,
	`typeId` INT NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`cost` DECIMAL(19, 4),
	`payout` DECIMAL(19, 4),
	PRIMARY KEY (`insuranceEntryId`),
	UNIQUE KEY (`typeId`, `name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------------------------------

DROP TABLE IF EXISTS `merIskVolume`;
CREATE TABLE `merIskVolume` (
	`date` DATE NOT NULL,
	`iskVolume` BIGINT,
	PRIMARY KEY (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `merMoneySupply`;
CREATE TABLE `merMoneySupply` (
	`date` DATE NOT NULL,
	`character` BIGINT,
	`corporation` BIGINT,
	`total` BIGINT,
	PRIMARY KEY (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `merProdDestMine`;
CREATE TABLE `merProdDestMine` (
	`date` DATE NOT NULL,
	`produced` BIGINT,
	`destroyed` BIGINT,
	`mined` BIGINT,
	PRIMARY KEY (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `merRegStat`;
CREATE TABLE `merRegStat` (
	`regStatEntryId` INT AUTO_INCREMENT,
	`date` DATE NOT NULL,
	`regionId` INT NOT NULL,
	`exports` BIGINT,
	`exportsm3` BIGINT,
	`imports` BIGINT,
	`importsm3` BIGINT,
	`netExports` BIGINT,
	`netImports` BIGINT,
	`produced` BIGINT,
	`destroyed` BIGINT,
	`mined` BIGINT,
	`traded` BIGINT,
	PRIMARY KEY (`regStatEntryId`),
	FOREIGN KEY (`regionId`)
		REFERENCES `region`(`regionId`),
	UNIQUE KEY `ux_merRegStat_regionId_date` (`regionId`, `date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `merSinkFaucet`;
CREATE TABLE `merSinkFaucet` (
	`sinkFaucetEntryId` INT AUTO_INCREMENT,
	`date` DATE NOT NULL,
	`keyText` VARCHAR(64) NOT NULL,
	`category` VARCHAR(64),
	`faucet` BIGINT,
	`sink` BIGINT,
	`sortValue` BIGINT,
	PRIMARY KEY (`sinkFaucetEntryId`),
	UNIQUE KEY `ux_merSinkFaucet_keyText_date` (`keyText` , `date`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------------------------------

DROP VIEW IF EXISTS `vJitaBestBuy`;
DROP VIEW IF EXISTS `vJitaBestSell`;
DROP VIEW IF EXISTS `vAmarrBestBuy`;
DROP VIEW IF EXISTS `vAmarrBestSell`;

CREATE VIEW vJitaBestBuy AS SELECT typeId, MAX(price) AS best FROM marketOrder WHERE locationId=60003760 AND isBuyOrder=1 GROUP BY typeId, locationId;
CREATE VIEW vJitaBestSell AS SELECT typeId, MIN(price) AS best FROM marketOrder WHERE locationId=60003760 AND isBuyOrder=0 GROUP BY typeId, locationId;
CREATE VIEW vAmarrBestBuy AS SELECT typeId, MAX(price) AS best FROM marketOrder WHERE locationId=60008494 AND isBuyOrder=1 GROUP BY typeId, locationId;
CREATE VIEW vAmarrBestSell AS SELECT typeId, MIN(price) AS best FROM marketOrder WHERE locationId=60008494 AND isBuyOrder=0 GROUP BY typeId, locationId;

