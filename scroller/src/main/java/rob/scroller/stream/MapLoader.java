package rob.scroller.stream;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;

public class MapLoader implements IMapLoader
{
	private Logger logger = LoggerFactory.getLogger(MapLoader.class);

	@Override
	public MapArchive loadMapArchive(String filename)
	{
		MapArchive archive = new MapArchive();

		ZipFile zFile = null;

		try
		{
			zFile = new ZipFile(filename);

			ZipEntry entry = zFile.getEntry("map.json");

			if (entry == null)
			{
				logger.error("Could not find map.json");
				throw new MapException("Could not find map.json");
			}

			InputStream inputStream = zFile.getInputStream(entry);

			try
			{
				MMap map = load(inputStream);
				archive.setGameMap(map);

				Map<String, byte[]> resources = new HashMap<String, byte[]>();
				
				for (MEntity entity : map.getAllEntities())
				{
					if (!resources.containsKey(entity.getSprite()))
					{
						ZipEntry entityEntry = zFile.getEntry(entity.getSprite());
						InputStream entityStream = zFile.getInputStream(entityEntry);

						resources.put(entity.getSprite(), ByteStreams.toByteArray(entityStream));
					}
					
					entity.setSpriteData(resources.get(entity.getSprite()));
				}

			} finally
			{
				inputStream.close();
			}
		} catch (IOException e)
		{
			throw new MapException();
		} finally
		{
			if (zFile != null)
			{
				try
				{
					zFile.close();
				} catch (IOException e)
				{
					logger.warn("Could not close {}", zFile.getName());
				}
			}
		}

		return archive;
	}

	public MMap load(InputStream stream)
	{
		ObjectMapper mapper = new ObjectMapper();

		try
		{
			return mapper.readValue(stream, MMap.class);
		} catch (JsonParseException e)
		{
			e.printStackTrace();
		} catch (JsonMappingException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
