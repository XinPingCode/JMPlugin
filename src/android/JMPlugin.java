package JMPlugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PermissionHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.util.Log;
import java.io.File;
import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Build;
import android.content.ComponentName;
import android.widget.Toast;
import android.util.Log;
import android.content.Context;

/**
 * This class echoes a string called from JavaScript.
 */
public class JMPlugin extends CordovaPlugin {
    CallbackContext cbc;

    String[] permissions = { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE };

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.cbc = callbackContext;
        if (action.equals("isInstall")) {
            String message = args.getString(0);
            this.isInstall(callbackContext);
            return true;
        } else if (action.equals("jumpMeeting")) {
            String name = args.getString(0);
            String password = args.getString(1);
            this.jumpMeeting(name, password, callbackContext);
            return true;
        } else if (action.equals("jumpMeetingNum")) {
            String name = args.getString(0);
            String password = args.getString(1);
            String number = args.getString(2);
            this.jumpMeetingNum(name, password, number, callbackContext);
            return true;
        } else if (action.equals("startMeeting")) {
            String message = args.getString(0);
            this.startMeeting(message, callbackContext);
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("demo", "233");
        // super.onActivityResult(requestCode, resultCode, data);
        String requestCodeS = String.valueOf(requestCode);
        String resultCodeS = String.valueOf(resultCode);
        Log.v("requestCode", requestCodeS);
        Log.v("resultCode", resultCodeS);
        // Log.v("data", data);
        PluginResult pluginR = new PluginResult(PluginResult.Status.OK, resultCodeS);
        pluginR.setKeepCallback(true);
        this.cbc.sendPluginResult(pluginR);
        if (resultCode == 0) {
            return;
        }
        if (requestCode == 1 && resultCode == 4) {
            String id = data.getStringExtra("meetingId");
            Log.v("test", id);
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, id);
            pluginResult.setKeepCallback(true);
            this.cbc.sendPluginResult(pluginResult);
            Context context = this.cordova.getActivity().getApplicationContext();
            Toast.makeText(context, id, Toast.LENGTH_SHORT);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void isInstall(CallbackContext callbackContext) {

        getPermission();
        String backb = "unknow";
        if (isInstallPackage("cn.redcdn.meeting")) {
            backb = "true";
        } else {
            backb = "false";
        }
        callbackContext.success(backb);
    }

    private boolean isInstallPackage(String packageName) {
        return new File("/data/data/" + packageName).exists();
        // cn.redcdn.hvs
    }

    public void getPermission() {
        if (hasPermisssion()) {
        } else {
            PermissionHelper.requestPermissions(this, 0, permissions);
        }
    }

    public boolean hasPermisssion() {
        for (String p : permissions) {
            if (!PermissionHelper.hasPermission(this, p)) {
                return false;
            }
        }
        return true;
    }

    private void jumpMeeting(String name, String password, CallbackContext callbackContext) {
        // Intent intent = new Intent();
        // intent.setData(Uri.parse("com.channelst.wovideoapp://"));
        // this.cordova.startActivityForResult(this, intent, 0);

        // ComponentName componetName = new ComponentName("cn.redcdn.meeting",
        // "cn.redcdn.meeting.accountoperate.activity.GoToMeetin");
        // Intent intent = new Intent();
        // intent.setComponent(componetName);

        // this.cordova.startActivityForResult(this, intent, 0);
        // callbackContext.success("jumpMeeting");

        ComponentName componetName = new ComponentName("cn.redcdn.meeting",
                "cn.redcdn.meeting.accountoperate.activity.GoToMeetin");
        Intent intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("password", password);
        intent.setComponent(componetName);
        // this.cordova.setActivityResultCallback(this);
        // cordova.setActivityResultCallback (this);

        this.cordova.startActivityForResult(this, intent, 1);

        // this.cordova.setActivityResultCallback(this);
        // this.cordova.getActivity().startActivityForResult(intent,0);

        callbackContext.success("jumpMeeting");
    }

    private void jumpMeetingNum(String name, String password, String number, CallbackContext callbackContext) {
        // Intent intent = new Intent();
        // intent.setData(Uri.parse("com.channelst.wovideoapp://"+arg));
        // this.cordova.startActivityForResult(this, intent, 0);
        // ComponentName componetName = new ComponentName("cn.redcdn.meeting",
        // "cn.redcdn.meeting.accountoperate.JoinMeetingActivity");
        // Intent intent = new Intent();
        // intent.putExtra("number", arg);
        // intent.setComponent(componetName);
        // this.cordova.startActivityForResult(this, intent, 0);
        // callbackContext.success("jumpMeetingNum");

        ComponentName componetName = new ComponentName("cn.redcdn.meeting",
                "cn.redcdn.meeting.accountoperate.JoinMeetingActivity");
        Intent intent = new Intent();
        // intent.putExtra("number", "50002079");
        // intent.putExtra("number", "80002101");
        // intent.putExtra("name", "30000009");
        // intent.putExtra("password", "1002");
        intent.putExtra("number", number);
        intent.putExtra("name", name);
        intent.putExtra("password", password);

        intent.setComponent(componetName);
        this.cordova.startActivityForResult(this, intent, 0);
    }

    private void startMeeting(String arg, CallbackContext callbackContext) {
        // Intent intent = new Intent();
        // intent.setData(Uri.parse("com.channelst.wovideoapp://"));
        // this.cordova.startActivityForResult(this, intent, 0);
        ComponentName componetName = new ComponentName("cn.redcdn.meeting",
                "cn.redcdn.meeting.accountoperate.activity.GoToMeetin");
        Intent intent = new Intent();
        intent.setComponent(componetName);

        this.cordova.startActivityForResult(this, intent, 0);

        callbackContext.success("startMeeting");
    }
}
