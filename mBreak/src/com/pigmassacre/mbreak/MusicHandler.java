package com.pigmassacre.mbreak;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.audio.Music;
import com.pigmassacre.mbreak.objects.Assets;

public class MusicHandler {

	private static Music currentMusic;
	private static List<Music> musicList = new LinkedList<Music>();
	private static int currentPosition = 0;

	public static void addSong(String name) {
		musicList.add(Assets.getMusic(name));
	}
	
	public static void setSong(String name) {
		if (currentMusic != null) {
			currentMusic.stop();
		}
		currentMusic = Assets.getMusic(name);
	}
	
	public static boolean play() {
		if (currentMusic != null) {
			currentMusic.play();
			return true;
		}
		return false;
	}
	
	public static boolean pause() {
		if (currentMusic != null) {
			currentMusic.pause();
			return true;
		}
		return false;
	}
	
	public static boolean stop() {
		if (currentMusic != null) {
			currentMusic.stop();
			return true;
		}
		return false;
	}
	
	public static void next() {
		if (currentMusic != null) {
			currentMusic.stop();
		}
		if (++currentPosition > musicList.size() - 1) {
			currentPosition = 0;
		}
		currentMusic = musicList.get(currentPosition);
		play();
	}
	
	public static void prev() {
		if (currentMusic != null) {
			currentMusic.stop();
		}
		if (--currentPosition < 0) {
			currentPosition = musicList.size() - 1;
		}
		currentMusic = musicList.get(currentPosition);
		play();
	}
	
	public static boolean isLooping() {
		if (currentMusic != null) {
			return currentMusic.isLooping();
		}
		return false;
	}
	
	public static void setLooping(boolean looping) {
		if (currentMusic != null) {
			currentMusic.setLooping(looping);
		}
	}
	
	public static boolean isPlaying() {
		if (currentMusic != null) {
			return currentMusic.isPlaying();
		}
		return false;
	}
	
}
