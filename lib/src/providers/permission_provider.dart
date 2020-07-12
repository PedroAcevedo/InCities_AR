import 'package:permission_handler/permission_handler.dart';

class PermissionsService {
  
  Future<PermissionStatus> requestPermission({Function onPermissionDenied}) async {
    Map<Permission, PermissionStatus> statuses = await [
      Permission.camera, Permission.storage,
    ].request();
    return statuses[Permission.camera];
  }

}
