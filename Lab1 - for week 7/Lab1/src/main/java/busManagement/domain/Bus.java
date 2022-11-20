package busManagement.domain;

import java.util.Objects;

public class Bus extends BaseEntity<Long> {

    private Long companyId;
    private Long driverId;
    private String modelName;

    public Bus(Long companyId, Long driverId, String modelName) {

        this.companyId = companyId;
        this.driverId = driverId;
        this.modelName = modelName;
    }

    public Long getDriverId() {
        return driverId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof Bus)) return false;

        Bus bus = (Bus) o;

        return getCompanyId().equals(bus.getCompanyId()) && getDriverId().equals(bus.getDriverId()) && getModelName().equals(bus.getModelName());
    }

    @Override
    public String toString() {
        return "Bus{" +
                "companyId=" + companyId +
                ", driverId=" + driverId +
                ", modelName='" + modelName + '\'' +
                '}';
    }
}
