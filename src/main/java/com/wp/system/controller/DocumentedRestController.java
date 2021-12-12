package com.wp.system.controller;

import com.wp.system.exception.ServiceErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "201", description = "Successful created"),
        @ApiResponse(responseCode = "400", description = "Given invalid data", content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServiceErrorResponse.class))
        }),
        @ApiResponse(responseCode = "500", description = "Error in system", content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServiceErrorResponse.class))
        }),
        @ApiResponse(responseCode = "404", description = "Not found", content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServiceErrorResponse.class))
        }),
        @ApiResponse(responseCode = "403", description = "No access for operation", content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServiceErrorResponse.class))
        }),
        @ApiResponse(responseCode = "401", description = "No auth", content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServiceErrorResponse.class))
        }),
})
public class DocumentedRestController {
}
