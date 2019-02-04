package com.example.mazda.requestpermission.Model;

public class Rute {
    String orgCode;
    String desCode;
    String orgName;
    String desName;
    String tipeBus;
    String hargaTiket;
    String departureDate;
    String routeCode;
    String routeName;
    String routeInfo;

    public Rute(String orgCode, String desCode, String orgName, String desName, String tipeBus, String hargaTiket, String departureDate, String routeCode, String routeName, String routeInfo) {
        this.orgCode = orgCode;
        this.desCode = desCode;
        this.orgName = orgName;
        this.desName = desName;
        this.tipeBus = tipeBus;
        this.hargaTiket = hargaTiket;
        this.departureDate = departureDate;
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.routeInfo = routeInfo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getDesCode() {
        return desCode;
    }

    public void setDesCode(String desCode) {
        this.desCode = desCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDesName() {
        return desName;
    }

    public void setDesName(String desName) {
        this.desName = desName;
    }

    public String getTipeBus() {
        return tipeBus;
    }

    public void setTipeBus(String tipeBus) {
        this.tipeBus = tipeBus;
    }

    public String getHargaTiket() {
        return hargaTiket;
    }

    public void setHargaTiket(String hargaTiket) {
        this.hargaTiket = hargaTiket;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(String routeInfo) {
        this.routeInfo = routeInfo;
    }
}
