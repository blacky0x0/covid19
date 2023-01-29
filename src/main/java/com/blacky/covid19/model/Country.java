package com.blacky.covid19.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Country")
public class Country implements Serializable {

    @JsonIgnore
    private String id;
    private String country;
    private String slug;
    private String iso2;

}
