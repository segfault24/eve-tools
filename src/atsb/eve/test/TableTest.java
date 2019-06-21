package atsb.eve.test;

import java.sql.Connection;
import java.sql.SQLException;

import atsb.eve.db.InvTypeTable;
import atsb.eve.db.MapTables;
import atsb.eve.db.MarketGroupTable;
import atsb.eve.model.Constellation;
import atsb.eve.model.InvType;
import atsb.eve.model.MarketGroup;
import atsb.eve.model.Region;
import atsb.eve.model.SolarSystem;
import atsb.eve.model.Station;
import atsb.eve.util.DbInfo;
import atsb.eve.util.DbPool;

public class TableTest {

	private Connection db;

	public TableTest() throws SQLException {
		DbPool dbPool = new DbPool(new DbInfo());
		db = dbPool.acquire();
	}

	public void runTests() throws SQLException {
		testMapTables();
		testTypeGroupTables();
	}

	private void testMapTables() throws SQLException {
		Region r = new Region();
		r.setReigonId(999999);
		r.setRegionName("milky way");
		r.setDescription("this is a fictional region");
		MapTables.upsert(db, r);

		Constellation c = new Constellation();
		c.setConstellationId(777);
		c.setConstellationName("orion");
		c.setRegionId(999999);
		MapTables.upsert(db, c);

		SolarSystem s = new SolarSystem();
		s.setSolarSystemId(333);
		s.setSolarSystemName("earth");
		s.setConstellationId(777);
		s.setRegionId(999999);
		s.setX(1);
		s.setY(2);
		s.setZ(3);
		s.setSecurity(4.5);
		MapTables.upsert(db, s);

		Station st = new Station();
		st.setStationId(6969);
		st.setStationName("segfault");
		st.setSolarSystemId(333);
		st.setConstellationId(777);
		st.setRegionId(999999);
		MapTables.upsert(db, st);

		Region q = MapTables.findRegionBySystem(db, 333);
		assert (q.getRegionName().equals("milky way"));

		MapTables.delete(db, r);
		MapTables.delete(db, c);
		MapTables.delete(db, s);
		MapTables.delete(db, st);
	}

	private void testTypeGroupTables() throws SQLException {
		int before = InvTypeTable.selectAllIds(db).size();

		InvType i = new InvType();
		i.setTypeId(999999999);
		i.setTypeName("cellphone");
		i.setDescription("time waster");
		i.setGroupId(6);
		i.setMarketGroupId(42);
		i.setMass(0.1);
		i.setVolume(0.01);
		i.setPublished(true);
		InvTypeTable.upsert(db, i);

		MarketGroup g = new MarketGroup();
		g.setMarketGroupId(42);
		g.setMarketGroupName("distractions");
		g.setDescription("things that cause accidents");
		g.setParentGroupId(0);
		g.setHasTypes(true);
		MarketGroupTable.upsert(db, g);

		assert (InvTypeTable.selectAllIds(db).size() == before + 1);
		assert (InvTypeTable.getMarketableTypeIds(db).get(0) == 999999999);

		InvTypeTable.delete(db, i);
		MarketGroupTable.delete(db, g);
	}

	public static void main(String[] args) {
		try {
			new TableTest().runTests();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
