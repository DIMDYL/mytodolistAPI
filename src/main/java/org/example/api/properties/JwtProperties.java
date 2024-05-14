package org.example.api.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtProperties {
  private String key;
  private int expiration;
}
