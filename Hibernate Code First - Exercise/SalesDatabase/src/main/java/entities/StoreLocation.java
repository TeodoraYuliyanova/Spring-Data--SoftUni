package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "store_location")
public class StoreLocation {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_name",nullable = false)
    private String locationName;

    @OneToMany(mappedBy = "storeLocationId")
    private Set<Sale> sales;

    public StoreLocation() {
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    protected void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    protected void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
