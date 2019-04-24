package domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Address")
@NamedQueries({
        @NamedQuery(name = "Address.findAll", query = "select b from Address b")
})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;
    private String country;
    private String city;
    private Integer zipCode;
    private String street;
    private Integer number;
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
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "City")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "ZipCode")
    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    @Basic
    @Column(name = "Street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "Number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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
}
