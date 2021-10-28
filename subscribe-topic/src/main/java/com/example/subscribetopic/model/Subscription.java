package com.example.subscribetopic.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Subscription {

    @JsonProperty("subsId")
    private String subsId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("firstname")
    private String firstname;

    /**
     * Subscriber gender
     */
    public enum GenderEnum {
        MALE("Male"),

        FEMALE("Female");

        private String value;

        GenderEnum(String value) {
            this.value = value;
        }

        public String toString() {
            return String.valueOf(value);
        }

        public static GenderEnum fromValue(String text) {
            for (GenderEnum b : GenderEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("gender")
    private GenderEnum gender;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("has_consent")
    private Boolean hasConsent;

    @JsonProperty("newsletter_id")
    private String newsletterId;

    public Subscription() {}

    public Subscription(String subsId, String firstname, String email, String gender, Date dateOfBirth,
                        boolean hasConsent, String newsletterId) {
        this.subsId = subsId;
        this.firstname = firstname;
        this.email = email;
        this.gender = GenderEnum.valueOf(gender);
        this.dateOfBirth = dateOfBirth;
        this.hasConsent = hasConsent;
        this.newsletterId = newsletterId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean isHasConsent() {
        return hasConsent;
    }

    public void setHasConsent(Boolean hasConsent) {
        this.hasConsent = hasConsent;
    }

    public String getNewsletterId() {
        return newsletterId;
    }

    public void setNewsletterId(String newsletterId) {
        this.newsletterId = newsletterId;
    }
    public String getSubsId() {
        return subsId;
    }

    public void setSubsId(String subsId) {
        this.subsId = subsId;
    }
}
