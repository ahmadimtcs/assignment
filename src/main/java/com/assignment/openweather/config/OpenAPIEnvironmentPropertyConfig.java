package com.assignment.openweather.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "service")
public class OpenAPIEnvironmentPropertyConfig {

  private RestClient restClient;

  private String appId;

  private String units;


  public RestClient getRestClient() {
    return restClient;
  }

  public OpenAPIEnvironmentPropertyConfig setRestClient(RestClient restClient) {
    this.restClient = restClient;
    return this;
  }

  public String getAppId() {
    return appId;
  }

  public OpenAPIEnvironmentPropertyConfig setAppId(String appId) {
    this.appId = appId;
    return this;
  }

  public String getUnits() {
    return units;
  }

  public OpenAPIEnvironmentPropertyConfig setUnits(String units) {
    this.units = units;
    return this;
  }

  public static class RestClient {

    private String baseUrl;
    private String searchUrl;
    private String dataUrl;

    public String getBaseUrl() {
      return baseUrl;
    }

    public RestClient setBaseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
      return this;
    }

    public String getSearchUrl() {
      return searchUrl;
    }

    public RestClient setSearchUrl(String searchUrl) {
      this.searchUrl = searchUrl;
      return this;
    }

    public String getDataUrl() {
      return dataUrl;
    }

    public RestClient setDataUrl(String dataUrl) {
      this.dataUrl = dataUrl;
      return this;
    }
  }
}
