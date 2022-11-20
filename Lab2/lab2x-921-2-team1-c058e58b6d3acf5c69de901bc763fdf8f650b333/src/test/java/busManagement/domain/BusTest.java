package busManagement.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class BusTest {

    private static Long cid1;
    private static Long cid2;
    private static Long did1;
    private static Long did2;
    private static String modelName1;
    private static String modelName2;

    private static Bus bus1;
    private static Bus bus2;
    private static Bus bus3;

    @Before
    public void setUp() {

        cid1 = 101L;
        cid2 = 102L;
        did1 = 201L;
        did2 = 202L;
        modelName1 = "ElectricBus";
        modelName2 = "TurboBus";
    }

    @Test
    public void testGetCompanyId() {
        bus1 = new Bus(Long.valueOf(cid1), Long.valueOf(did1), String.copyValueOf(modelName1.toCharArray()));
        bus1.setId(0L);

        assertEquals(cid1, bus1.getCompanyId(), "CompanyIds should be equal");
    }

    @Test
    public void testGetDriverId() {
        bus2 = new Bus(Long.valueOf(cid2), Long.valueOf(did2), String.copyValueOf(modelName1.toCharArray()));
        bus2.setId(1L);

        assertEquals(did2, bus2.getDriverId(), "DriverIds should be equal");
    }

    @Test
    public void testGetModelName() {
        bus3 = new Bus(Long.valueOf(cid1), Long.valueOf(did1), String.copyValueOf(modelName1.toCharArray()));
        bus3.setId(2L);

        assertEquals(modelName1, bus3.getModelName(), "ModelNames should be equal");
    }

    @Test
    public void testSetCompanyId() {
        bus1 = new Bus(Long.valueOf(cid1), Long.valueOf(did1), String.copyValueOf(modelName1.toCharArray()));
        bus1.setId(0L);

        bus1.setCompanyId(cid2);
        assertEquals(cid2, bus1.getCompanyId(), "CompanyIds should be equal");
    }

    @Test
    public void testSetDriverId() {
        bus2 = new Bus(Long.valueOf(cid2), Long.valueOf(did2), String.copyValueOf(modelName1.toCharArray()));
        bus2.setId(1L);

        bus2.setDriverId(did1);
        assertEquals(did1, bus2.getDriverId(), "DriverIds should be equal");
    }

    @Test
    public void testSetModelName() {
        bus3 = new Bus(Long.valueOf(cid1), Long.valueOf(did1), String.copyValueOf(modelName1.toCharArray()));
        bus3.setId(2L);

        bus3.setModelName(modelName2);
        assertEquals(modelName2, bus3.getModelName(), "ModelNames should be equal");
    }

    @Test
    public void testEquals() {
        bus1 = new Bus(Long.valueOf(cid1), Long.valueOf(did1), String.copyValueOf(modelName1.toCharArray()));
        bus1.setId(0L);

        bus2 = new Bus(Long.valueOf(cid2), Long.valueOf(did2), String.copyValueOf(modelName1.toCharArray()));
        bus2.setId(1L);

        bus3 = new Bus(Long.valueOf(cid1), Long.valueOf(did1), String.copyValueOf(modelName1.toCharArray()));
        bus3.setId(2L);

        assertEquals(true, bus1.equals(bus1), "Comparison of 2 buses should be reflexive");
        assertEquals(false, bus2.equals(modelName1), "Comparison of a bus to an instance of a different class should return false.");
        assertEquals(true, bus1.equals(bus3), "2 buses with the same cid, did and ModelName should be equal");
    }

    @Test
    public void testToString() {
        bus1 = new Bus(Long.valueOf(cid1), Long.valueOf(did1), String.copyValueOf(modelName1.toCharArray()));
        bus1.setId(0L);

        assertEquals("Bus{" + "companyId=" + cid1 + ", driverId=" + did1 + ", modelName='" + modelName1 + '\'' + '}', bus1.toString());
    }
}