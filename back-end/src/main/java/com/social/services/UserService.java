package com.social.services;

import com.social.dao.ParcelRepository;
import com.social.entities.Parcel;
import com.social.model.AvailabilityRequest;
import com.social.model.CoordsUpdateRequest;
import com.social.model.VerifyNfcRequest;
import java.text.Normalizer;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.social.dao.UserRepository;
import com.social.entities.User;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

	public static String KEY = "AIzaSyBMmm_Q-BzFlb5Hx-b74uHOA4f2PY9jxXE";
	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ParcelRepository parcelRepository;

	@Autowired
	AndroidPushNotificationsService androidPushNotificationsService;

	@Autowired
	NotificationProvider notificationProvider;

	public User save(User user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}


	public User update(User user) {
		return userRepository.save(user);
	}

	public User find(String userName) {
		return userRepository.findOneByUsername(userName);
	}

	public User find(Long id) {
		return userRepository.findOne(id);
	}

	public boolean verifyNfc(VerifyNfcRequest verifyNfcRequest) {

		User user = userRepository.findOneByUsername(verifyNfcRequest.getUserName());

		return user.getNfcTag().equals(verifyNfcRequest.getNfcTag());


	}


	public void setAvailability(AvailabilityRequest availability) {

		User user = find(availability.getUserName());
		user.setStartActive(new Date());
		user.setMaxWeight(Integer.valueOf(availability.getWeight()));
		user.setEndActive(DateUtils.addHours(new Date(), Integer.valueOf(availability.getHours())));
		user.setLat(Double.valueOf(availability.getLat()));
		user.setLng(Double.valueOf(availability.getLng()));

		user.setStatus("READY");
		userRepository.save(user);


	}

	public User findByPhone(String phone) {
		return userRepository.findByPhone(phone);
	}


	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User getUserById(Long id) {
		return userRepository.findById(id);
	}

	public User getUserByUserName(String userName) {
		return userRepository.findByUsername(userName);
	}

	public void updateCourierCoords(CoordsUpdateRequest coordsUpdateRequest) {
		User courier = userRepository.findByUsername(coordsUpdateRequest.getUserName());
		courier.setLng(Double.valueOf(coordsUpdateRequest.getLongitude()));
		courier.setLat(Double.valueOf(coordsUpdateRequest.getLatitude()));
		Parcel parcel = parcelRepository.findByCourierId(Long.valueOf(courier.getId())).stream()
			.filter(r -> r.getStatus().equals("PICKED_UP")).collect(
				Collectors.toList()).get(0);
		User reciver = findByPhone(parcel.getEndPhone());
		User sender = findByPhone(parcel.getStartPhone());
		RestTemplate rest = new RestTemplate();
		String res = rest.getForObject("https://maps.googleapis.com/maps/api/geocode/json?latlng={lat},{lng}&key={key}",
			String.class, courier.getLng().toString(), courier.getLat().toString(), KEY);

		JSONObject json = new JSONObject(res);
		JSONArray ja_data = json.getJSONArray("results");
		String adress = stripAccents(ja_data.getJSONObject(0).getString("formatted_address"));

		androidPushNotificationsService.send(notificationProvider
			.createNotification(reciver.getUsername(), "New parcel", "You have new parcel: start",
				new Date().toString() +'\n'+ 		"Parcel`s location:" + '\n' + adress));

		androidPushNotificationsService.send(notificationProvider
			.createNotification(sender.getUsername(), "New parcel", "You have new parcel: start",
				new Date().toString() +'\n'+ 	"Parcel`s location:" + '\n' + adress));
	}

	public String getSaldo(String username){

		return userRepository.findByUsername(username).getSaldo().toString();
	}
public void setCourierReady(String userName){

		User user = userRepository.findOneByUsername(userName);
		if(user.getEndActive().getTime() < new Date().getTime()){
user.setStatus("INACTIVE");
		}
		else {
			user.setStatus("READY");
		}
		update(user);

}
	public static String stripAccents(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return s;

	}
}
