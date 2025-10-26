package com.a.introduction.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
*  Même objectif que pour les deux questions précedentes
* */

class GildedRoseCBackstagePassesTest {

    private GildedRose app;
    private Item backstagePass;

    @BeforeEach
    void setUp() {
        // Création d'un Backstage Pass par défaut
        backstagePass = new Item("Backstage passes", 15, 3);
        app = new GildedRose(new Item[] { backstagePass });
    }

    @Test
    void should_increase_quality_by_1_when_sellIn_greater_than_10() {
        app.updateQuality();

        assertEquals("Backstage passes", backstagePass.name);
        assertEquals(14, backstagePass.sellIn);
        assertEquals(4, backstagePass.quality);
    }

    @Test
    void should_increase_quality_by_2_when_sellIn_between_6_and_10() {
        backstagePass.sellIn = 7;
        backstagePass.quality = 3;

        app.updateQuality();

        assertEquals("Backstage passes", backstagePass.name);
        assertEquals(6, backstagePass.sellIn);
        assertEquals(5, backstagePass.quality);
    }

    @Test
    void should_increase_quality_by_3_when_sellIn_between_0_and_5() {
        backstagePass.sellIn = 4;
        backstagePass.quality = 3;

        app.updateQuality();

        assertEquals("Backstage passes", backstagePass.name);
        assertEquals(3, backstagePass.sellIn);
        assertEquals(6, backstagePass.quality);
    }

    @Test
    void should_drop_quality_to_0_after_concert() {
        backstagePass.sellIn = 0;
        backstagePass.quality = 40;

        app.updateQuality();

        assertEquals("Backstage passes", backstagePass.name);
        assertEquals(-1, backstagePass.sellIn);
        assertEquals(0, backstagePass.quality);
    }
}
