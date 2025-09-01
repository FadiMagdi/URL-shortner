package com.FadiMagdi.URL_Shortner.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "url")
public class URL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer ID;

@Column(nullable = false,unique = true,columnDefinition = "TEXT")
    String mainURL;
@Column(nullable = false,unique = true)
    String shortCode;

    Date createdAt;

    Date updatedAt;

    int accessCount;

    public URL(String shortCode, String mainURL, Date createdAt) {
        this.shortCode = shortCode;
        this.mainURL = mainURL;
        this.createdAt = createdAt;

    }
}
