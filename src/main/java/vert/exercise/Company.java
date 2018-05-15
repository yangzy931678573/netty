package vert.exercise;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by Administrator on 2018/3/23.
 * Description :
 */
//@DataObject(generateConverter = true)
public class Company {

    private int id;
    private String companyNo;
    private String name;
    private String address;

    public Company() {
    }

    public Company(Company other) {
        this.id = other.id;
        this.companyNo = other.companyNo;
        this.name = other.name;
        this.address = other.address;
    }
   /* public Company(JsonObject json) {
        CompanyConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CompanyConverter.toJson(this, json);
        return json;
    }
*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
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
}
