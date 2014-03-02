package com.pigmassacre.mbreak;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class Traversal implements InputProcessor {
	
	public static List<Menu> menus;
	private Camera camera;
	
	public Traversal(Camera camera) {
		this.camera = camera;
		if (menus == null)
			menus = new ArrayList<Menu>();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		selectItems(screenX, screenY);
		Vector3 coords = new Vector3(screenX, screenY, 0);
		camera.unproject(coords);
		for (Menu menu : menus) {
			for (Item item : menu.items) {
				if (item.isPointerOverItem(coords.x, coords.y) && item.selected) {
					System.out.println("PRESSED " + item);
					item.executeFunction();
				}
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		selectItems(screenX, screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		selectItems(screenX, screenY);
		return false;
	}

	private void selectItems(int screenX, int screenY) {
		Vector3 coords = new Vector3(screenX, screenY, 0);
		camera.unproject(coords);
		for (Menu menu : menus) {
			for (Item item : menu.items) {
				if (item.isPointerOverItem(coords.x, coords.y)) {
					for (Item otherItem : menu.items) {
						otherItem.selected = false;
					}
					for (Menu otherMenu : menu.otherMenus) {
						for (Item anotherItem : otherMenu.items) {
							anotherItem.selected = false;
						}
					}
					item.selected = true;
				}
			}
		}
	}
	
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}	
	
//		elif ((event.type == KEYDOWN and event.key == K_LEFT) or (event.type == JOYHATMOTION and event.value[0] == -1) or
//			  (event.type == JOYAXISMOTION and event.axis == 0 and event.value <= -0.25)):
//			# We try to traverse the menu to the left.
//			select_left_or_right(list_of_menus, True)
//		elif ((event.type == KEYDOWN and event.key == K_RIGHT) or (event.type == JOYHATMOTION and event.value[0] == 1) or
//			  (event.type == JOYAXISMOTION and event.axis == 0 and event.value >= 0.25)):
//			# To the right...
//			select_left_or_right(list_of_menus, False)
//		elif ((event.type == KEYDOWN and event.key == K_UP) or (event.type == JOYHATMOTION and event.value[1] == 1) or
//			  (event.type == JOYAXISMOTION and event.axis == 1 and event.value <= -0.25)):
//			# Up...
//			select_up_or_down(list_of_menus, True)
//		elif ((event.type == KEYDOWN and event.key == K_DOWN) or (event.type == JOYHATMOTION and event.value[1] == -1) or
//			  (event.type == JOYAXISMOTION and event.axis == 1 and event.value >= 0.25)):
//			# Or down.
//			select_up_or_down(list_of_menus, False)
//
//	def select_left_or_right(list_of_menus, left):
//		"""
//		Tries to traverse to the left or right of the currently selected item.
//
//		The algorithm favors the item with the least x-distance to the selected item AND the least y-distance, with
//		focus on the y-distance being as low as possible.
//
//		What does this mean? Consider the following scenario:
//
//		Item A is to the left of item B.
//		Item B is in the middle of the screen.
//		Item C is to the left of item B, but to the right of item A. It is in the middle of item A and item B.
//
//		However, item C is above item B and item A.
//
//		Item A and item B are at the same y-level.
//
//		Item B is selected.
//
//		If the function is told to traverse left, it will select item A, even though item C has a shorter x-distance to
//		item B.
//
//		This is because item B has a higher y-distance than item A (item A has 0 y-distance!).
//
//		So, the algorithm favors items with as small y-distance as possible, and if several items have the same (smallest)
//		y-distance, it favors the one with the smallest x-distance of those items.
//
//		If there is no item to the left or right of the selected item, nothing happens.
//		"""
//		# First, we want to get the selected item and fill up a list of possible items to traverse to.
//		selected_item = get_selected_item(list_of_menus)
//		list_of_possible = fill_list_of_possible(list_of_menus)
//
//		# Depending on if we're going left or right, we filter in different ways.
//		if left:
//			# If we're going left, we only want the items that have an x-value SMALLER than our selected items x-value.
//			list_of_possible = filter(lambda x: (x.x + (x.get_width() / 2.0)) < (selected_item.x + (selected_item.get_width() / 2.0)), list_of_possible)
//		else:
//			# If we're going right, we only want the items that have an x-value LARGER than our selected items x-value.
//			list_of_possible = filter(lambda x: (x.x + (x.get_width() / 2.0)) > (selected_item.x + (selected_item.get_width() / 2.0)), list_of_possible)
//
//		# Find out if there if any of the possible items are in the same menu as the selected item. If they are
//		# we only care about those items.
//		same_menu_items = list(list_of_possible)
//		for a_menu in list_of_menus:
//			same_menu_items = filter(lambda x: x in a_menu.items, same_menu_items)
//
//		if len(list_of_possible) > 0:
//			# If there are any items left, we want to retain the item which has the least y-difference AND least x-difference.
//			if len(same_menu_items) == 0:
//				# However, we only care about the y-difference if we're traveling from one menu to another.
//				least_y_difference = 999999
//				for an_item in list_of_possible:
//					# Find the least y difference.
//					this_difference = abs((an_item.y + (an_item.get_height() / 2.0)) - (selected_item.y + (selected_item.get_height() / 2.0)))
//					if this_difference < least_y_difference:
//						least_y_difference = this_difference
//
//				# We want to retain the items which have a y-difference equal to the least y-difference.
//				list_of_possible = filter(lambda x: abs((x.y + (x.get_height() / 2.0)) - (selected_item.y + (selected_item.get_height() / 2.0))) == least_y_difference, list_of_possible)
//			else:
//				# If we're traveling within the same menu, we only care about those menu items and the x-positions.
//				list_of_possible = same_menu_items
//			
//			least_x_difference = 999999
//			for an_item in list_of_possible:
//				# Find the least x difference.
//				if abs(an_item.x - selected_item.x) < least_x_difference:
//					least_x_difference = abs(an_item.x - selected_item.x)
//
//			# From these remaining items, we want to pick the item which has the least x difference.
//			list_of_possible = filter(lambda x: abs(x.x - selected_item.x) == least_x_difference, list_of_possible)
//			
//			# Finally, we unselect the selected item, and then select the first item in the FINAL list of possible items. This list
//			# SHOULD only ever contain one item, but if it does contain more for some reason we just pick the first.
//			selected_item.selected = False
//			list_of_possible[0].selected = True
//
//	def select_up_or_down(list_of_menus, up):
//		"""
//		Tries to traverse up or down of the currently selected item.
//
//		The algorithm favors the item with the least y-distance to the selected item AND the least x-distance, with
//		focus on the x-distance being as low as possible.
//
//		What does this mean? Consider the following scenario:
//
//		Item A is above item B.
//		Item B is in the middle of the screen.
//		Item C is above item B and below item A (in the middle of item A and B), but also to the left of both item A and B.
//
//		Item A and item B are at the same x-level.
//
//		Item B is selected.
//
//		If the function is told to traverse up, it will select item A, even though item C has a shorter y-distance to
//		item B.
//
//		This is because item B has a higher x-distance than item A (item A has 0 x-distance!).
//
//		So, the algorithm favors items with as small x-distance as possible, and if several items have the same (smallest)
//		x-distance, it favors the one with the smallest y-distance of those items.
//
//		If there is no item to the up or down of the selected item, nothing happens.
//		"""
//		# First, we want to get the selected item and fill up a list of possible items to traverse to.
//		selected_item = get_selected_item(list_of_menus)
//		list_of_possible = fill_list_of_possible(list_of_menus)
//
//		# Depending on if we're going up or down, we want to filter in different ways.
//		if up:
//			# If we're going up, we only want the items which have a y-value SMALLER than the selected item.
//			list_of_possible = filter(lambda x: (x.y + (x.get_height() / 2.0)) < (selected_item.y + (selected_item.get_height() / 2.0)), list_of_possible)
//		else:
//			# If we're going down, we only want the items which have a y-value LARGER than the selected item.
//			list_of_possible = filter(lambda x: (x.y + (x.get_height() / 2.0)) > (selected_item.y + (selected_item.get_height() / 2.0)), list_of_possible)
//
//		# Find out if there if any of the possible items are in the same menu as the selected item.
//		same_menu_items = list(list_of_possible)
//		for a_menu in list_of_menus:
//			same_menu_items = filter(lambda x: x in a_menu.items, same_menu_items)
//
//		if len(list_of_possible) > 0:
//			# If there are any items left, we want to retain the item which has the least y-difference AND least x-difference.
//			if len(same_menu_items) == 0:
//				# However, we only care about the x difference if we're traveling from one menu to another.
//				least_x_difference = 999999
//				for an_item in list_of_possible:
//					# Find the least x difference.
//					this_difference = abs((an_item.x + (an_item.get_width() / 2.0)) - (selected_item.x + (selected_item.get_width() / 2.0)))
//					if this_difference < least_x_difference:
//						least_x_difference = this_difference
//
//				# We want to retain the items which have a x-difference equal to the least x-difference.
//				list_of_possible = filter(lambda x: abs((x.x + (x.get_width() / 2.0)) - (selected_item.x + (selected_item.get_width() / 2.0))) == least_x_difference, list_of_possible)
//			else:
//				# If we're traveling within the same menu, we only care about the items in that menu and the y-positions.
//				list_of_possible = same_menu_items
//
//			least_y_difference = 999999
//			for an_item in list_of_possible:
//				# Find the least y difference.
//				if abs(an_item.y - selected_item.y) < least_y_difference:
//					least_y_difference = abs(an_item.y - selected_item.y)
//
//			# From these remaining items, we want to pick the item which has the least y difference.
//			list_of_possible = filter(lambda x: abs(x.y - selected_item.y) == least_y_difference, list_of_possible)
//			
//			# Finally, we unselect the selected item, and then select the first item in the FINAL list of possible items. This list
//			# SHOULD only ever contain one item, but if it does contain more for some reason we just pick the first.
//			selected_item.selected = False
//			list_of_possible[0].selected = True
//
//
//	def get_selected_item(list_of_menus):
//		"""
//		Returns the first selected item in the given list_of_menus.
//		"""
//		for a_menu in list_of_menus:
//			for item in a_menu.items:
//				if item.selected:
//					return item
//
//	def fill_list_of_possible(list_of_menus):
//		"""
//		Returns a list that contains all the items in all the menus in list_of_menus.
//		"""
//		list_of_possible = []
//		for a_menu in list_of_menus:
//			for item in a_menu.items:
//				list_of_possible.append(item)
//		return list_of_possible
	
}
