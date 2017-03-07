// Copyright (c) Yugabyte, Inc.
package com.yugabyte.yw.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.fasterxml.jackson.annotation.JsonBackReference;

import play.data.validation.Constraints;

@Entity
public class AvailabilityZone extends Model {

  @Id
  public UUID uuid;

  @Column(length = 25, nullable = false)
  public String code;

  @Column(length = 100, nullable = false)
  @Constraints.Required
  public String name;

  @Constraints.Required
  @Column(nullable = false)
  @ManyToOne
  @JsonBackReference
  public Region region;

  @Column(nullable = false, columnDefinition = "boolean default true")
  public Boolean active = true;
  public Boolean isActive() { return active; }
  public void setActiveFlag(Boolean active) { this.active = active; }

  @Column(length = 50, nullable = false)
  public String subnet;

  /**
   * Query Helper for Availability Zone with primary key
   */
  public static final Find<UUID, AvailabilityZone> find = new Find<UUID,AvailabilityZone>(){};

  public static AvailabilityZone create(Region region, String code, String name, String subnet) {
    AvailabilityZone az = new AvailabilityZone();
    az.region = region;
    az.code = code;
    az.name = name;
    az.subnet = subnet;
    az.save();
    return az;
  }

  public static List<AvailabilityZone> getAZsForRegion(UUID regionUUID) {
    return find.where().eq("region_uuid", regionUUID).findList();
  }

  public static AvailabilityZone getByCode(String code) {
    return find.where().eq("code", code).findUnique();
  }

  public static AvailabilityZone get(UUID zoneUuid) {
    return find.byId(zoneUuid);
  }

  @JsonBackReference
  public Provider getProvider() {
    String providerQuery =
        "select p.uuid, p.code, p.name from provider p where p.uuid = :p_uuid";
    RawSql rawSql = RawSqlBuilder.parse(providerQuery).create();
    Query<Provider> query = Ebean.find(Provider.class);
    query.setRawSql(rawSql);
    query.setParameter("p_uuid", region.provider.uuid);
    return query.findUnique();
  }
}
