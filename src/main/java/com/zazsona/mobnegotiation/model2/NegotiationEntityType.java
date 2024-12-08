package com.zazsona.mobnegotiation.model2;

public enum NegotiationEntityType {
    ELDER_GUARDIAN(0),
    WITHER_SKELETON(1),
    STRAY(2),
    HUSK(3),
    ZOMBIE_VILLAGER(4),
    SKELETON_HORSE(5),
    ZOMBIE_HORSE(6),
    DONKEY(7),
    MULE(8),
    EVOKER(9),
    VEX(10),
    VINDICATOR(11),
    ILLUSIONER(12),
    CREEPER(13),
    SKELETON(14),
    SPIDER(15),
    GIANT(16),
    ZOMBIE(17),
    SLIME(18),
    GHAST(19),
    ZOMBIFIED_PIGLIN(20),
    ENDERMAN(21),
    CAVE_SPIDER(22),
    SIVLERFISH(23),
    BLAZE(24),
    MAGMA_CUBE(25),
    ENDER_DRAGON(26),
    WITHER(27),
    BAT(28),
    WITCH(29),
    ENDERMITE(30),
    GUARDIAN(31),
    SHULKER(32),
    PIG(33),
    SHEEP(34),
    COW(35),
    CHICKEN(36),
    SQUID(37),
    WOLF(38),
    MOOSHROOM(39),
    SNOW_GOLEM(40),
    OCELOT(41),
    IRON_GOLEM(42),
    HORSE(43),
    RABBIT(44),
    POLAR_BEAR(45),
    LLAMA(46),
    PARROT(47),
    VILLAGER(48),
    TURTLE(49),
    PHANTOM(50),
    COD(51),
    SALMON(52),
    PUFFERFISH(53),
    TROPICAL_FISH(54),
    DROWNED(55),
    DOLPHIN(56),
    CAT(57),
    PANDA(58),
    PILLAGR(59),
    RAVAGER(60),
    TRADER_LLAMA(61),
    WANDERING_TRADER(62),
    FOX(63),
    BEE(64),
    HOGLIN(65),
    PIGLIN(66),
    STRIDER(67),
    ZOGLIN(68),
    PIGLIN_BRUTE(69),
    AXOLOTL(70),
    GLOW_SQUID(71),
    GOAT(72),
    ALLAY(73),
    FROG(74),
    TADPOLE(75),
    WARDEN(76),
    CAMEL(77),
    SNIFFER(78),
    ARMADILLO(79),
    BOGGED(80);

    private int id;

    NegotiationEntityType(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    /**
     * Gets the corresponding enum entry by id
     * @param id the ID to match
     * @return the {@link NegotiationEntityType} with a matching ID, or null if no match found.
     */
    public static NegotiationEntityType fromId(int id) {
        for (NegotiationEntityType entityType : NegotiationEntityType.values()) {
            if (entityType.getId() == id)
                return entityType;
        }
        return null;
    }
}
