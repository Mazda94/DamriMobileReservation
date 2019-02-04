package com.example.mazda.requestpermission.Model;

public class Terminal {
    int id;
    String orgCode;
    String desCode;
    String terminalName;
    String terminalCity;
    String status;
    String longitude;
    String latitude;

    public Terminal(int id, String orgCode, String desCode, String terminalName, String terminalCity, String status, String longitude, String latitude) {
        this.id = id;
        this.orgCode = orgCode;
        this.desCode = desCode;
        this.terminalName = terminalName;
        this.terminalCity = terminalCity;
        this.status = status;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getTerminalCity() {
        return terminalCity;
    }

    public void setTerminalCity(String terminalCity) {
        this.terminalCity = terminalCity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}


