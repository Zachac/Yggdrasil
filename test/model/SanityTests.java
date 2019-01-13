package model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import model.world.Coordinate3D;
import model.world.Coordinate3D.Direction;
import model.world.Coordinate4D;
import model.world.Tile;
import model.world.Tile.Biome;
import model.world.TileTraverser.SearchField;
import model.world.World;
import parser.commands.GoCommand;
import parser.commands.LookCommand;

public class SanityTests {
	
	
	private static World w;
	
	@BeforeClass
	public static void init() {
		w = World.get(); {
			for (int i = -37; i <= 37; i++) {
				for (int j = -37; j <= 37; j++) {
					if (i == 0 &&  j == 0) {
						continue;
					}
					w.addTile(new Coordinate4D(i, j, 0, 0), Biome.GRASS);
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
		Assert.assertEquals(north, GoCommand.MoveEvent.getNewlyExposedSearchField(Direction.N, w.getRoot()));
	}

	@Test 
	public void testSouthSearchField() {
		SearchField south = new SearchField(-LookCommand.DEFAULT_LOOK, -LookCommand.DEFAULT_LOOK, -LookCommand.DEFAULT_LOOK, LookCommand.DEFAULT_LOOK, 0);
		Assert.assertEquals(south, GoCommand.MoveEvent.getNewlyExposedSearchField(Direction.S, w.getRoot()));
	}

	@Test 
	public void testEastSearchField() {
		SearchField east = new SearchField(-LookCommand.DEFAULT_LOOK, LookCommand.DEFAULT_LOOK, LookCommand.DEFAULT_LOOK, LookCommand.DEFAULT_LOOK, 0);
		Assert.assertEquals(east, GoCommand.MoveEvent.getNewlyExposedSearchField(Direction.E, w.getRoot()));
	}

	@Test 
	public void testWestSearchField() {
		SearchField west = new SearchField(-LookCommand.DEFAULT_LOOK, -LookCommand.DEFAULT_LOOK, LookCommand.DEFAULT_LOOK, -LookCommand.DEFAULT_LOOK, 0);
		Assert.assertEquals(west, GoCommand.MoveEvent.getNewlyExposedSearchField(Direction.W, w.getRoot()));
	}
}
