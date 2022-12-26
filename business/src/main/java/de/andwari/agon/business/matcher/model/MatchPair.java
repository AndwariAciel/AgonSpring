package de.andwari.agon.business.matcher.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MatchPair {
    private Integer player1;
    private Integer player2;
}
