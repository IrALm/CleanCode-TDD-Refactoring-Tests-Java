package com.a.introduction.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
* Pour cette question , je fais pareil comme à l'exo1 .
* L'objectif reste le même : Rendre les noms de tests explicites,
                             Supprimer les commentaires redondants.
                             Factoriser la création des objets (Item et GildedRose) pour plus de lisibilité.
                             Réduire la duplication et rendre les tests plus faciles à maintenir.
*/

class GildedRoseBAgedBrieTest {

    private GildedRose app;
    private Item agedBrie;

    @BeforeEach
    void setUp() {
        // Initialise un Aged Brie par défaut
        agedBrie = new Item("Aged Brie", 4, 3);
        app = new GildedRose(new Item[] { agedBrie });
    }

    @Test
    void should_increase_quality_by_1_and_decrease_sellIn_by_1_for_non_expired_aged_brie() {
        app.updateQuality();

        assertEquals("Aged Brie", agedBrie.name);
        assertEquals(3, agedBrie.sellIn);
        assertEquals(4, agedBrie.quality);
    }

    @Test
    void should_increase_quality_by_2_for_expired_aged_brie() {
        // Article expiré
        agedBrie.sellIn = -1;
        agedBrie.quality = 3;

        app.updateQuality();

        assertEquals("Aged Brie", agedBrie.name);
        assertEquals(-2, agedBrie.sellIn);
        assertEquals(5, agedBrie.quality);
    }

    @Test
    void should_not_increase_quality_above_50_for_aged_brie() {
        agedBrie.quality = 50;

        app.updateQuality();

        assertEquals("Aged Brie", agedBrie.name);
        assertEquals(3, agedBrie.sellIn);
        assertEquals(50, agedBrie.quality);
    }
}
