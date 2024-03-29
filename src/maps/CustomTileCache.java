package maps;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jdesktop.swingx.mapviewer.TileCache;
 
 
/*
 * ============================================================================
 * Created:  May 12, 2007 1:49:17 AM
 * ============================================================================
 */
 
/**
 * Install custom cache hashmap implementation for SwingX mapviewer build 20070506
 * Map<URI, byte[]>
 */
public class CustomTileCache extends TileCache
{
	private Map<URI, BufferedImage> imgmap = new HashMap<URI, BufferedImage>();
	private LinkedList<URI> imgmapAccessQueue = new LinkedList<URI>();
	private int imagesize = 0;
	private Map<URI, byte[]> bytemap = new HashMap<URI, byte[]>();
	private LinkedList<URI> bytemapAccessQueue = new LinkedList<URI>();
	private int bytesize = 0;
	  
 	private static CustomTileCache instance;
 
	/**
	 * Private constructor, create instance through createInstance() methode
	 */
	private CustomTileCache()
	{ }
	
	/**
	 * Install custom cache
	 */
	public static synchronized CustomTileCache getInstance()
	{
		// Only one instance
		if (instance != null) {
			return instance;
		}
		
		instance = new CustomTileCache();
		return instance;
	}
	
	/**
	   * Put a tile image into the cache. This puts both a buffered image and array of bytes that make up the compressed image.
	   *
	   * @param uri
	   *            URI of image that is being stored in the cache
	   * @param bimg
	   *            bytes of the compressed image, ie: the image file that was loaded over the network
	   * @param img
	   *            image to store in the cache
	   */
	  public void put(URI uri, byte[] bimg, BufferedImage img) {
	      synchronized (bytemap) {
	          while (bytesize > 1000 * 1000 * 50) {
	              URI olduri = bytemapAccessQueue.removeFirst();
	              byte[] oldbimg = bytemap.remove(olduri);
	              bytesize -= oldbimg.length;
	              p("removed 1 img from byte cache");
	          }

	          bytemap.put(uri, bimg);
	          bytesize += bimg.length;
	          bytemapAccessQueue.addLast(uri);
	      }
	      addToImageCache(uri, img);
	  }

	  /**
	   * Returns a buffered image for the requested URI from the cache. This method must return null if the image is not in the cache. If the image is unavailable
	   * but it's compressed version *is* available, then the compressed version will be expanded and returned.
	   *
	   * @param uri URI of the image previously put in the cache
	   * @return the image matching the requested URI, or null if not available
	   * @throws java.io.IOException
	   */
	  public BufferedImage get(URI uri) throws IOException {
	      synchronized (imgmap) {
	          if (imgmap.containsKey(uri)) {
	              imgmapAccessQueue.remove(uri);
	              imgmapAccessQueue.addLast(uri);
	              return imgmap.get(uri);
	          }
	      }
	      synchronized (bytemap) {
	          if (bytemap.containsKey(uri)) {
	              p("retrieving from bytes");
	              bytemapAccessQueue.remove(uri);
	              bytemapAccessQueue.addLast(uri);
	              BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytemap.get(uri)));
	              addToImageCache(uri, img);
	              return img;
	          }
	      }
	      return null;
	  }

	  /**
	   * Request that the cache free up some memory. How this happens or how much memory is freed is up to the TileCache implementation. Subclasses can implement
	   * their own strategy. The default strategy is to clear out all buffered images but retain the compressed versions.
	   */
	  public void needMoreMemory() {
		  imgmapAccessQueue.clear();
	      imgmap.clear();
	      imagesize = 0;
	      p("HACK! need more memory: freeing up memory");
	  }

	    
	    
	  private void addToImageCache(final URI uri, final BufferedImage img) {
	      synchronized (imgmap) {
	          while (imagesize > 1000 * 1000 * 50) {
	              URI olduri = imgmapAccessQueue.removeFirst();
	              BufferedImage oldimg = imgmap.remove(olduri);
	              imagesize -= oldimg.getWidth() * oldimg.getHeight() * 4;
	              p("removed 1 img from image cache");
	          }

	          imgmap.put(uri, img);
	          imagesize += img.getWidth() * img.getHeight() * 4;
	          imgmapAccessQueue.addLast(uri);
	      }
	      p("added to cache: " + " uncompressed = " + imgmap.keySet().size() + " / " + imagesize / 1000 + "k" + " compressed = " + bytemap.keySet().size()
	              + " / " + bytesize / 1000 + "k");
	  }

	  private void p(String string) {
	        //System.out.println(string);
	  }
}
