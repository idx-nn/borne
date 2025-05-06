package nn.iamj.borne.modules.util.entity;

import lombok.Getter;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public final class MobUtils {

    private MobUtils() {}

    @Getter
    private static final List<EntityType> monsters;
    @Getter
    private static final List<EntityType> animals;
    @Getter
    private static final List<EntityType> bosses;

    static {
        monsters = new ArrayList<>();
        monsters.add(EntityType.ZOMBIE);
        monsters.add(EntityType.SLIME);
        monsters.add(EntityType.CREEPER);
        monsters.add(EntityType.SKELETON);
        monsters.add(EntityType.BLAZE);
        monsters.add(EntityType.ENDERMITE);
        monsters.add(EntityType.ENDERMAN);
        monsters.add(EntityType.PIGLIN);
        monsters.add(EntityType.PIGLIN_BRUTE);
        monsters.add(EntityType.ZOMBIFIED_PIGLIN);
        monsters.add(EntityType.PILLAGER);
        monsters.add(EntityType.EVOKER_FANGS);
        monsters.add(EntityType.GHAST);
        monsters.add(EntityType.WITHER_SKELETON);
        monsters.add(EntityType.HUSK);
        monsters.add(EntityType.SHULKER);
        monsters.add(EntityType.CAVE_SPIDER);
        monsters.add(EntityType.SPIDER);
        monsters.add(EntityType.ZOMBIE_VILLAGER);
        monsters.add(EntityType.GUARDIAN);
        monsters.add(EntityType.MAGMA_CUBE);
        monsters.add(EntityType.PHANTOM);
        monsters.add(EntityType.VINDICATOR);
        monsters.add(EntityType.VEX);
        monsters.add(EntityType.WITCH);
        monsters.add(EntityType.STRAY);
        monsters.add(EntityType.SILVERFISH);
        monsters.add(EntityType.DROWNED);

        //-------------------------------------------
        animals = new ArrayList<>();
        animals.add(EntityType.PIG);
        animals.add(EntityType.COW);
        animals.add(EntityType.SHEEP);
        animals.add(EntityType.TURTLE);
        animals.add(EntityType.POLAR_BEAR);
        animals.add(EntityType.HORSE);
        animals.add(EntityType.SKELETON_HORSE);
        animals.add(EntityType.ZOMBIE_HORSE);
        animals.add(EntityType.CHICKEN);
        animals.add(EntityType.MULE);
        animals.add(EntityType.LLAMA);
        animals.add(EntityType.OCELOT);
        animals.add(EntityType.WOLF);
        animals.add(EntityType.BAT);
        animals.add(EntityType.DONKEY);
        animals.add(EntityType.RABBIT);
        animals.add(EntityType.SALMON);
        animals.add(EntityType.TROPICAL_FISH);
        animals.add(EntityType.PUFFERFISH);
        animals.add(EntityType.DOLPHIN);
        animals.add(EntityType.PARROT);
        animals.add(EntityType.SQUID);
        animals.add(EntityType.PARROT);
        animals.add(EntityType.STRIDER);
        animals.add(EntityType.FOX);
        animals.add(EntityType.CAT);
        animals.add(EntityType.BEE);
        animals.add(EntityType.PANDA);

        //-------------------------------------------
        bosses = new ArrayList<>();
        bosses.add(EntityType.ENDER_DRAGON);
        bosses.add(EntityType.WITHER);
        bosses.add(EntityType.EVOKER);
        bosses.add(EntityType.ELDER_GUARDIAN);
    }

    public static boolean isMonster(EntityType type) {
        return monsters.contains(type);
    }
    public static boolean isAnimal(EntityType type) { return animals.contains(type); }
    public static boolean isBoss(EntityType type) { return bosses.contains(type); }

}
