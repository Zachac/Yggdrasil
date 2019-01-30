package model;

import org.ex.yggdrasil.model.world.Coordinate3D;
import org.ex.yggdrasil.model.world.Direction3D;
import org.ex.yggdrasil.model.world.Tile;
import org.ex.yggdrasil.model.world.Tile.Biome;
import org.ex.yggdrasil.model.world.TileTraverser.SearchField;
import org.ex.yggdrasil.model.world.World;
import org.ex.yggdrasil.parser.commands.GoCommand;
import org.ex.yggdrasil.parser.commands.LookCommand;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SanityTest {
	
	private static World w;
	
	@BeforeClass
	public static void init() {
		w = World.get(); {
			for (int i = -37; i <= 37; i++) {
				for (int j = -37; j <= 37; j++) {
					if (i == 0 &&  j == 0) {
						continue;
					}
					w.addTile(new Coordinate3D(i, j, 0), Biome.GRASS);
				}
			}
		}
	}
	
	@Test
	public void testGetTile() {
		for (int i = -37; i <= 37; i++) {
			for (int j = -37; j <= 37; j++) {
				Coordinate3D position = new Coordinate3D(i, j, 0);
				Tile t = w.getTile(position);
				Assert.assertNotNull(t);
				Assert.assertEquals(position, t.getPosition());
			}
		}
	}

	@Test 
	public void testNorthSearchField() {
		SearchField north = new SearchField(LookCommand.DEFAULT_LOOK, -LookCommand.DEFAULT_LOOK, LookCommand.DEFAULT_LOOK, LookCommand.DEFAULT_LOOK, 0);
		Assert.assertEquals(north, GoCommand.MoveEvent.getNewlyExposedSearchField(Direction3D.N, w.getRoot()));
	}

	@Test 
	public void testSouthSearchField() {
		SearchField south = new SearchField(-LookCommand.DEFAULT_LOOK, -LookCommand.DEFAULT_LOOK, -LookCommand.DEFAULT_LOOK, LookCommand.DEFAULT_LOOK, 0);
		Assert.assertEquals(south, GoCommand.MoveEvent.getNewlyExposedSearchField(Direction3D.S, w.getRoot()));
	}

	@Test 
	public void testEastSearchField() {
		SearchField east = new SearchField(-LookCommand.DEFAULT_LOOK, LookCommand.DEFAULT_LOOK, LookCommand.DEFAULT_LOOK, LookCommand.DEFAULT_LOOK, 0);
		Assert.assertEquals(east, GoCommand.MoveEvent.getNewlyExposedSearchField(Direction3D.E, w.getRoot()));
	}

	@Test 
	public void testWestSearchField() {
		SearchField west = new SearchField(-LookCommand.DEFAULT_LOOK, -LookCommand.DEFAULT_LOOK, LookCommand.DEFAULT_LOOK, -LookCommand.DEFAULT_LOOK, 0);
		Assert.assertEquals(west, GoCommand.MoveEvent.getNewlyExposedSearchField(Direction3D.W, w.getRoot()));
	}
}
