package com.parking.management.system.domain;

import java.util.Objects;

public class Admin {

    private int id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private int accountId;

    public Admin(String name, String address, String email, String phone, int account_id) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.accountId = account_id;
    }

    public Admin(int id, String name, String address, String email, String phone, int account_id) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.accountId = account_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAccount_id() {
        return accountId;
    }

    public void setAccount_id(int account_id) {
        this.accountId = account_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return id == admin.id &&
                accountId == admin.accountId &&
                name.equals(admin.name) &&
                address.equals(admin.address) &&
                email.equals(admin.email) &&
                phone.equals(admin.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, email, phone, accountId);
    }
}
