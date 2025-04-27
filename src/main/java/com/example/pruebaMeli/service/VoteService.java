package com.example.pruebaMeli.service;

import com.example.pruebaMeli.client.FoodOutletClient;
import com.example.pruebaMeli.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final FoodOutletClient foodOutletClient;

    @Autowired
    public VoteService(FoodOutletClient foodOutletClient) {
        this.foodOutletClient = foodOutletClient;
    }

    public int getVoteCount(String cityName, int estimatedCost) {
        if (cityName == null || cityName.isBlank()) {
            throw new IllegalArgumentException("El nombre de la ciudad no puede estar vacío.");
        }
        if (estimatedCost < 0) {
            throw new IllegalArgumentException("El costo estimado debe ser un valor positivo.");
        }

        ApiResponse respuesta;
        int totalVotes=0;
        int page=1;

        do {
            respuesta = foodOutletClient.getFoodOutlets(cityName, estimatedCost, page);

            if (respuesta == null || respuesta.data() == null || respuesta.data().isEmpty()) {
                return -1;
            }

            totalVotes += respuesta.data().stream()
                    .mapToInt(outlet -> outlet.user_rating().votes())
                    .sum();

            page++;

        } while (page <= respuesta.total_pages());

        return totalVotes == 0 ? -1 : totalVotes;
    }
}
