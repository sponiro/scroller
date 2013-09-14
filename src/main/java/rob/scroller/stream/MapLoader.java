package rob.scroller.stream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
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

		try
		{
			MMap map = readZipFile(filename);
			archive.setGameMap(map);
			
		} catch (MapException e)
		{
			
		}

		return archive;
	}

	private MMap readZipFile(String filename) throws MapException
	{
		MMap map = null;

		ZipFile zipFile = null;

		try
		{
			zipFile = new ZipFile(filename);

			ZipEntry zipEntry = zipFile.getEntry("map.json");

			if (zipEntry == null)
			{
				logger.error("Could not find map.json");
				throw new MapException("Could not find map.json");
			}

			InputStream inputStream = zipFile.getInputStream(zipEntry);

			try
			{
				map = load(inputStream);

				Map<String, byte[]> resources = new HashMap<String, byte[]>();

				for (MEntity entity : map.getAllEntities())
				{
					if (!resources.containsKey(entity.getSprite()))
					{
						ZipEntry entityEntry = zipFile.getEntry(entity.getSprite());
						InputStream entityStream = zipFile.getInputStream(entityEntry);

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
			if (zipFile != null)
			{
				try
				{
					zipFile.close();
				} catch (IOException e)
				{
					logger.warn("Could not close {}", zipFile.getName());
				}
			}
		}

		return map;
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
