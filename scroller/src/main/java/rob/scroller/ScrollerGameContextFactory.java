package rob.scroller;

import org.jbox2d.dynamics.World;

public interface ScrollerGameContextFactory
{

	ScrollerGameContext create(World world);
	
}
