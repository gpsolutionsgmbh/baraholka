package com.kramar.data.dbo;

import com.kramar.data.dbo.AbstractAuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "oauth_clients")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = false)
public class OAuthClient extends AbstractAuditableEntity {

    private static final long serialVersionUID = 6174587150921516716L;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "client_resources")
    private String resources;

    public Set<String> getResources() {
        return (resources == null) ? null : Arrays.stream(this.resources.split(",")).collect(Collectors.toSet());
    }

}
