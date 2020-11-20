-- -----------------------------------------------------------------------------
-- Conventions used by this database:
--
-- tables and columns are named in camelCase
-- indices are named in the following convention:
--   px_<table>_<col>              primary KEY (single column, sometimes on surrogate indices)
--   fx_<table>_<col1>_<col2>_...  foreign indices (constrain to other tables)
--   ux_<table>_<col1>_<col2>_...  unique indices (constraints on this table)
--   ix_<table>_<col1>_<col2>_...  alternative indices (faster operations)
-- additionally, indices will typically be defined in px, fx, ux, ix order
-- views are prefixed with 'v'
-- derived tables are prefixed with 'd'
-- -----------------------------------------------------------------------------

USE `eve`;

-- -----------------------------------------------------------------------------

CREATE TABLE `region` (
	`regionId` int(11) NOT NULL,
	`regionName` varchar(100) NOT NULL,
	`description` text DEFAULT NULL,
	PRIMARY KEY (`regionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `constellation` (
	`constellationId` int(11) NOT NULL,
	`constellationName` varchar(100) NOT NULL,
	`regionId` int(11) NOT NULL,
	PRIMARY KEY (`constellationId`),
	KEY `ix_constellation_regionId` (`regionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

CREATE TABLE `marketGroup` (
	`marketGroupId` int(11) NOT NULL,
	`parentGroupId` int(11) DEFAULT NULL,
	`marketGroupName` varchar(100) DEFAULT NULL,
	`description` varchar(3000) DEFAULT NULL,
	`hasTypes` tinyint(1) DEFAULT NULL,
	PRIMARY KEY (`marketGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `alliance` (
	`allianceId` INT,
	`allianceName` VARCHAR(64),
	`ticker` VARCHAR(8),
	`dateFounded` DATE,
	`executorCorp` INT,
	PRIMARY KEY (`allianceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

CREATE TABLE `character` (
	`charId` INT,
	`charName` VARCHAR(64),
	`corpId` INT,
	`allianceId` INT,
	`birthday` DATE,
	PRIMARY KEY (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

CREATE TABLE `property` (
	`id` INT AUTO_INCREMENT,
	`propertyName` VARCHAR(255) NULL,
	`propertyValue` VARCHAR(255) NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `ux_property_propertyName` (`propertyName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `kvStore`  (
	`id` INT AUTO_INCREMENT,
	`key` VARCHAR(255) NULL,
	`value` VARCHAR(255) NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `ux_kvStore_key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `dirtUser` (
	`userId` INT AUTO_INCREMENT,
	`username` VARCHAR(64) NOT NULL,
	`name` VARCHAR(64),
	`hash` VARCHAR(255) NOT NULL,
	`dateCreated` TIMESTAMP NOT NULL,
	`lastLogin` TIMESTAMP NOT NULL,
	`admin` BOOLEAN NOT NULL DEFAULT FALSE,
	`disabled` BOOLEAN NOT NULL DEFAULT FALSE,
	PRIMARY KEY (`userId`),
	UNIQUE KEY `ux_dirtUser_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

CREATE TABLE `doctrine` (
	`doctrine` INT AUTO_INCREMENT,
	`listId` INT NOT NULL,
	`locationId` BIGINT NOT NULL,
	`quantity` INT NOT NULL,
	`target` INT NOT NULL,
	`lowestPrice` DECIMAL(19,4) NOT NULL DEFAULT 0,
	PRIMARY KEY (`doctrine`),
	FOREIGN KEY (`listId`)
		REFERENCES `dirtList` (`listId`)
		ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

CREATE TABLE `dirtNotification` (
	`notifId` INT AUTO_INCREMENT,
	`time` TIMESTAMP NOT NULL,
	`userId` INT NOT NULL,
	`alertId` INT,
	`typeId` INT,
	`title` VARCHAR(64),
	`text` VARCHAR(1024),
	`acknowledged` BOOLEAN NOT NULL DEFAULT FALSE,
	`sent` BOOLEAN NOT NULL DEFAULT FALSE,
	PRIMARY KEY (`notifId`),
	FOREIGN KEY (`userId`)
		REFERENCES `dirtUser`(`userId`)
		ON DELETE CASCADE,
	KEY `ix_dirtNotification_userId` (`userId`),
	KEY `ix_dirtNotification_alertId` (`alertId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `fortchain` (
	`systemId` int(11) NOT NULL,
	`superDocking` BOOLEAN DEFAULT FALSE,
	PRIMARY KEY (`systemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `taskLog` (
	`taskLogId` INT AUTO_INCREMENT,
	`taskName` VARCHAR(255) NOT NULL,
	`startTime` timestamp NOT NULL,
	`finishTime` timestamp NOT NULL,
	`duration` int(11) NOT NULL,
	`success` boolean NOT NULL DEFAULT FALSE,
	`error` varchar(255),
	PRIMARY KEY (`taskLogId`),
	KEY `ix_taskLog_taskName` (`taskName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `apiReq` (
	`apiReqName` VARCHAR(64) NOT NULL,
	`etag` VARCHAR(64),
	PRIMARY KEY (`apiReqName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

-- -----------------------------------------------------------------------------


CREATE TABLE `contractType` (
	`id` INT NOT NULL,
	`value` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `contractType` (`id`,`value`) VALUES (1,"Unknown");
INSERT INTO `contractType` (`id`,`value`) VALUES (2,"Item Exchange");
INSERT INTO `contractType` (`id`,`value`) VALUES (3,"Auction");
INSERT INTO `contractType` (`id`,`value`) VALUES (4,"Courier");
INSERT INTO `contractType` (`id`,`value`) VALUES (5,"Loan");

CREATE TABLE `contractStatus` (
	`id` INT NOT NULL,
	`value` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `contractStatus` (`id`,`value`) VALUES (1,"Outstanding");
INSERT INTO `contractStatus` (`id`,`value`) VALUES (2,"In Progress");
INSERT INTO `contractStatus` (`id`,`value`) VALUES (3,"Finished Issuer");
INSERT INTO `contractStatus` (`id`,`value`) VALUES (4,"Finished Contractor");
INSERT INTO `contractStatus` (`id`,`value`) VALUES (5,"Finished");
INSERT INTO `contractStatus` (`id`,`value`) VALUES (6,"Cancelled");
INSERT INTO `contractStatus` (`id`,`value`) VALUES (7,"Rejected");
INSERT INTO `contractStatus` (`id`,`value`) VALUES (8,"Failed");
INSERT INTO `contractStatus` (`id`,`value`) VALUES (9,"Deleted");
INSERT INTO `contractStatus` (`id`,`value`) VALUES (10,"Reversed");
INSERT INTO `contractStatus` (`id`,`value`) VALUES (11,"Unknown");

CREATE TABLE `contractAvailability` (
	`id` INT NOT NULL,
	`value` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `contractAvailability` (`id`,`value`) VALUES (1,"Public");
INSERT INTO `contractAvailability` (`id`,`value`) VALUES (2,"Personal");
INSERT INTO `contractAvailability` (`id`,`value`) VALUES (3,"Corporation");
INSERT INTO `contractAvailability` (`id`,`value`) VALUES (4,"Alliance");

CREATE TABLE `contract` (
	`contractId` INT NOT NULL,
	`type` INT NOT NULL,
	`status` INT NOT NULL,
	`availability` INT NOT NULL,
	`issuerId` INT NOT NULL,
	`issuerCorpId` INT NOT NULL,
	`forCorp` BOOLEAN,
	`assigneeId` INT NOT NULL,
	`acceptorId` INT NOT NULL,
	`dateIssued` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`dateExpired` TIMESTAMP NOT NULL,
	`dateAccepted` TIMESTAMP,
	`dateCompleted` TIMESTAMP,
	`title` VARCHAR(255),
	`price` DECIMAL(19,4),
	`startLocationId` BIGINT,
	`endLocationId` BIGINT,
	`daysToComplete` INT,
	`reward` DECIMAL(19,4),
	`collateral` DECIMAL(19,4),
	`buyout` DECIMAL(19,4),
	`volume` DECIMAL(19,4),
	PRIMARY KEY (`contractId`),
	FOREIGN KEY (`type`)
		REFERENCES `contractType`(`id`),
	FOREIGN KEY (`status`)
		REFERENCES `contractStatus`(`id`),
	FOREIGN KEY (`availability`)
		REFERENCES `contractAvailability`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `contractItem` (
	`contractItemId` INT AUTO_INCREMENT,
	`contractId` INT NOT NULL,
	`typeId` INT NOT NULL,
	`quantity` INT NOT NULL,
	`recordId` BIGINT NOT NULL,
	`included` BOOLEAN NOT NULL,
	`singleton` BOOLEAN NOT NULL,
	PRIMARY KEY (`contractItemId`),
	FOREIGN KEY (`contractId`)
		REFERENCES `contract`(`contractId`)
		ON DELETE CASCADE,
	KEY `ix_contractItem_contractId` (`contractId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `corpContract` (
	`contractId` INT NOT NULL,
	`type` INT NOT NULL,
	`status` INT NOT NULL,
	`availability` INT NOT NULL,
	`issuerId` INT NOT NULL,
	`issuerCorpId` INT NOT NULL,
	`forCorp` BOOLEAN,
	`assigneeId` INT NOT NULL,
	`acceptorId` INT NOT NULL,
	`dateIssued` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`dateExpired` TIMESTAMP NOT NULL,
	`dateAccepted` TIMESTAMP,
	`dateCompleted` TIMESTAMP,
	`title` VARCHAR(255),
	`price` DECIMAL(19,4),
	`startLocationId` BIGINT,
	`endLocationId` BIGINT,
	`daysToComplete` INT,
	`reward` DECIMAL(19,4),
	`collateral` DECIMAL(19,4),
	`buyout` DECIMAL(19,4),
	`volume` DECIMAL(19,4),
	PRIMARY KEY (`contractId`),
	FOREIGN KEY (`type`)
		REFERENCES `contractType`(`id`),
	FOREIGN KEY (`status`)
		REFERENCES `contractStatus`(`id`),
	FOREIGN KEY (`availability`)
		REFERENCES `contractAvailability`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `corpContractItem` (
	`contractItemId` INT AUTO_INCREMENT,
	`contractId` INT NOT NULL,
	`typeId` INT NOT NULL,
	`quantity` INT NOT NULL,
	`recordId` BIGINT NOT NULL,
	`included` BOOLEAN NOT NULL,
	`singleton` BOOLEAN NOT NULL,
	PRIMARY KEY (`contractItemId`),
	FOREIGN KEY (`contractId`)
		REFERENCES `corpContract`(`contractId`)
		ON DELETE CASCADE,
	KEY `ix_corpContractItem_contractId` (`contractId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------------------------------

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

CREATE TABLE `marketStat` (
	`statId` INT AUTO_INCREMENT,
	`regionId` INT NOT NULL,
	`typeId` INT NOT NULL,
	`ma30` BIGINT(20),
	`ma90` BIGINT(20),
	PRIMARY KEY (`statId`),
	UNIQUE KEY `ux_marketStat_regionId_typeId` (`regionId`, `typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `marketOrder` (
	`issued` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
	PRIMARY KEY (`orderId`),
	KEY `ix_marketOrder_typeId_regionId` (`typeId`, `regionId`),
	KEY `ix_marketOrder_locationId` (`locationId`),
	KEY `ix_marketOrder_regionId` (`regionId`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8;

CREATE TABLE `charOrder` (
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
	`charId` INT,
	PRIMARY KEY (`orderEntryId`),
	UNIQUE KEY `ux_charOrder_orderId` (`orderId`),
	KEY `ix_charOrder_charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

CREATE TABLE `merIskVolume` (
	`date` DATE NOT NULL,
	`iskVolume` BIGINT,
	PRIMARY KEY (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `merMoneySupply` (
	`date` DATE NOT NULL,
	`character` BIGINT,
	`corporation` BIGINT,
	`total` BIGINT,
	PRIMARY KEY (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `merProdDestMine` (
	`date` DATE NOT NULL,
	`produced` BIGINT,
	`destroyed` BIGINT,
	`mined` BIGINT,
	PRIMARY KEY (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

CREATE TABLE `dEntity` (
	`id` INT,
	`name` VARCHAR(64),
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `dLocation` (
	`locationId` BIGINT,
	`locationName` VARCHAR(100),
	PRIMARY KEY (`locationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------------------------------

CREATE VIEW vJitaBestBuy AS SELECT typeId, MAX(price) AS best FROM marketOrder WHERE locationId=60003760 AND isBuyOrder=1 GROUP BY typeId, locationId;
CREATE VIEW vJitaBestSell AS SELECT typeId, MIN(price) AS best FROM marketOrder WHERE locationId=60003760 AND isBuyOrder=0 GROUP BY typeId, locationId;
CREATE VIEW vAmarrBestBuy AS SELECT typeId, MAX(price) AS best FROM marketOrder WHERE locationId=60008494 AND isBuyOrder=1 GROUP BY typeId, locationId;
CREATE VIEW vAmarrBestSell AS SELECT typeId, MIN(price) AS best FROM marketOrder WHERE locationId=60008494 AND isBuyOrder=0 GROUP BY typeId, locationId;

