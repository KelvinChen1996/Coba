package com.example.asus.pikachise.model;

import com.example.asus.pikachise.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 10/12/2017.
 */

public class Franchisee {

    @SerializedName("franchisee_id")
    @Expose
    private String franchiseeId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("franchise_id")
    @Expose
    private String franchiseId;
    @SerializedName("agreement_franchisor_franchisee")
    @Expose
    private String agreementFranchisorFranchisee;
    @SerializedName("status_verified")
    @Expose
    private String statusVerified;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("telp")
    @Expose
    private String telp;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date_join")
    @Expose
    private String dateJoin;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(String franchiseId) {
        this.franchiseId = franchiseId;
    }

    public String getAgreementFranchisorFranchisee() {
        return agreementFranchisorFranchisee;
    }

    public void setAgreementFranchisorFranchisee(String agreementFranchisorFranchisee) {
        this.agreementFranchisorFranchisee = agreementFranchisorFranchisee;
    }

    public String getStatusVerified() {
        return statusVerified;
    }

    public void setStatusVerified(String statusVerified) {
        this.statusVerified = statusVerified;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(String dateJoin) {
        this.dateJoin = dateJoin;
    }

    public String getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(String franchiseeId) {
        this.franchiseeId = franchiseeId;
    }
}
