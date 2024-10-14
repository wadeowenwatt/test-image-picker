import 'package:flutter/services.dart';

class PermissionService {
  static const MethodChannel channel = MethodChannel("HEHEHE");

  static Future<bool> getImageAndVideoPermission() async {
    try {
      final bool result = await channel.invokeMethod('getImageAndVideoPermission');
      return result;
    } catch (e) {
      print(">>> $e");
      return false;
    }
  }

  static Future<bool> getImage() async {
    try {
      final bool result = await channel.invokeMethod('getImage');
      return result;
    } catch (e) {
      print(">>> $e");
      return false;
    }
  }
}
