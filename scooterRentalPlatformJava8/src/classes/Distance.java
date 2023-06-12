package classes;

// 0.001 -> 28-18
// 0.001 -> 23 km /hr

import java.io.IOException;
/**
 * 
 */
/**
 * @author allem40306
 *
 */
public class Distance {
	public static Double[] generate(Double lat, Double lng, double speed) throws IOException{
		Double nextLat, nextLng, dist;
		if (speed < 0) speed = 23.0;
		do
		{
			nextLat = lat + (Math.random() - 0.5) * (Math.round(speed * 0.001 / 23 * 100000) / 100000.0);
			nextLng = lng + (Math.random() - 0.5) * (Math.round(speed * 0.001 / 23 * 100000) / 100000.0);
			dist = MapDistUtil.GetDistance(lng , lat, nextLng, nextLat);
		}while(nextLat < 25.026708 || nextLat > 25.068277 || nextLng < 121.511162 || nextLng > 121.567045);
		return new Double[]{nextLat, nextLng, dist};
	}
	
	public static void main(String[] args) throws IOException{
		Double[] arr= generate(25.04, 121.511162, -1);
		System.out.println(25.04-arr[0]);
		System.out.println(121.511162-arr[1]);
	}
}