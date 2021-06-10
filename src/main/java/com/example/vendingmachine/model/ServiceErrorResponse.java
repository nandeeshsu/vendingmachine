package com.example.vendingmachine.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.Objects;

/**
 * REST API ServiceErrorResponse
 */

public class ServiceErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("message")
    private String message;
    @JsonProperty("serviceCode")
    private ServiceCodeEnum serviceCode;
    @JsonProperty("timestamp")
    private String timestamp;

    public ServiceErrorResponse message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Get message
     *
     * @return message
     */

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ServiceErrorResponse serviceCode(ServiceCodeEnum serviceCode) {
        this.serviceCode = serviceCode;
        return this;
    }

    /**
     * Service Code:
     * * `001` - Internal Service Error
     * * `002` - Bad Request
     *
     * @return serviceCode
     */

    public ServiceCodeEnum getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(ServiceCodeEnum serviceCode) {
        this.serviceCode = serviceCode;
    }

    public ServiceErrorResponse timestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * Get timestamp
     *
     * @return timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var serviceErrorResponse = (ServiceErrorResponse) o;
        return Objects.equals(this.message, serviceErrorResponse.message) &&
                Objects.equals(this.serviceCode, serviceErrorResponse.serviceCode) &&
                Objects.equals(this.timestamp, serviceErrorResponse.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, serviceCode, timestamp);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("class ServiceErrorResponse {\n");
        sb.append("    message: ").append(toIndentedString(message)).append("\n");
        sb.append("    serviceCode: ").append(toIndentedString(serviceCode)).append("\n");
        sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    /**
     * Service Code:
     * * `001` - Internal Service Error
     * * `002` - Bad Request
     */
    public enum ServiceCodeEnum {
        CODE001("001"),

        CODE002("002");

        private final String value;

        ServiceCodeEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static ServiceCodeEnum fromValue(String value) {
            for (ServiceCodeEnum b : ServiceCodeEnum.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}


