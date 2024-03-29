package domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;
    private String country;
    private String city;
    private Integer zipCode;
    private String street;
    private Integer number;
    private String bus;     //Added Bus for people who lives in appartements

    private Collection<User> usersByAddressId;

    @Id
    @Column(name = "AddressId")
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Basic
    @Column(name = "Country")
    public String getCountry() {
        if(this.country == null || this.country == "")
            return "België";
        return country;
    }

    public void setCountry(String country) {
        if (empty(country)) {
            throw new CRuntimeException("Land kan niet leeg zijn!");
        }
        this.country = country;
    }

    @Basic
    @Column(name = "City")
    public String getCity() {
        if(this.city == null)
            return "";
        return city;
    }

    public void setCity(String city) {
        if (empty(city)) {
            throw new CRuntimeException("Woonplaats kan niet leeg zijn!");
        }
        this.city = city;
    }

    @Basic
    @Column(name = "ZipCode")
    public Integer getZipCode() {
        if(this.country == null)
            return 0;
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        if (empty(zipCode.toString()) || zipCode == 0) {
            throw new CRuntimeException("Postcode kan niet leeg of 0 zijn!");
        }
        this.zipCode = zipCode;
    }

    @Basic
    @Column(name = "Street")
    public String getStreet() {
        if(this.country == null)
            return "";
        return street;
    }

    public void setStreet(String street) {
        if (empty(street)) {
            throw new CRuntimeException("Straat kan niet leeg zijn!");
        }
        this.street = street;
    }

    @Basic
    @Column(name = "Number")
    public Integer getNumber() {
        if(this.country == null)
            return 0;
        return number;
    }

    public void setNumber(Integer number) {
        if (empty(number.toString()) || number == 0) {
            throw new CRuntimeException("Huisnummer kan niet leeg of 0 zijn!");
        }
        this.number = number;
    }

    @Basic
    @Column(name = "Bus")
    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return addressId == address.addressId &&
                zipCode == address.zipCode &&
                number == address.number &&
                Objects.equals(country, address.country) &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, country, city, zipCode, street, number);
    }

    @OneToMany(mappedBy = "addressByAddressId")
    public Collection<User> getUsersByAddressId() {
        return usersByAddressId;
    }

    public void setUsersByAddressId(Collection<User> usersByAddressId) {
        this.usersByAddressId = usersByAddressId;
    }

    private boolean empty(String string) {
        if (string.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
