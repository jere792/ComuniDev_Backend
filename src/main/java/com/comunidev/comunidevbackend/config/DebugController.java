package com.comunidev.comunidevbackend.config;

import com.comunidev.comunidevbackend.shared.adapter.in.rest.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/debug")
@RequiredArgsConstructor
@Tag(name = "Debug", description = "Internal debug endpoints")
public class DebugController {

    private final MongoTemplate mongoTemplate;

    @GetMapping("/db-info")
    @Operation(summary = "Show MongoDB connection info and test connectivity")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDbInfo() {
        Map<String, Object> info = new LinkedHashMap<>();

        try {
            info.put("databaseName", mongoTemplate.getDb().getName());

            Document isMaster = mongoTemplate.executeCommand("{ isMaster: 1 }");
            if (isMaster.containsKey("hosts")) {
                info.put("hosts", isMaster.getList("hosts", String.class));
            } else if (isMaster.containsKey("me")) {
                info.put("host", isMaster.getString("me"));
            }

            mongoTemplate.executeCommand("{ ping: 1 }");
            info.put("connectionStatus", "CONNECTED");

        } catch (Exception e) {
            info.put("connectionStatus", "ERROR");
            info.put("error", e.getMessage());
        }

        return ResponseEntity.ok(ApiResponse.ok(info));
    }
}
