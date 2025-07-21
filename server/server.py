from flask import Flask, request, jsonify
from dotenv import load_dotenv
import platform
import subprocess
import os

load_dotenv()

app = Flask(__name__)

AUTH_TOKEN = os.getenv("AUTH_TOKEN")

@app.before_request
def check_auth_token():
    auth_header = request.headers.get("Authorization")
    if not AUTH_TOKEN or auth_header != f"Bearer {AUTH_TOKEN}":
        return jsonify({"error": "Unauthorized"}), 401

@app.route('/command', methods=['POST'])
def handle_command():
    data = request.get_json()
    cmd = data.get("command")
    mac = data.get("mac")

    if not cmd:
        return jsonify({"error": "Missing command"}), 400

    try:
        if cmd == "turnOff":
            return turn_off()
        elif cmd == "lock":
            return lock()
        elif cmd == "reboot":
            return reboot()
        else:
            return jsonify({"error": "Unknown command", "command": cmd}), 400
    except Exception as e:
        return jsonify({"error": str(e)}), 500

def turn_off():
    system = platform.system()
    if system == "Windows":
        os.system("shutdown /s /t 1")
    elif system == "Linux":
        os.system("shutdown now")
    else:
        raise NotImplementedError(f"Shutdown not implemented for {system}")
    return jsonify({"message": "Shutting down..."}), 200

def lock():
    system = platform.system()
    
    if system == "Windows":
        subprocess.run("rundll32.exe user32.dll,LockWorkStation")
    elif system == "Linux":
        subprocess.run("gnome-screensaver-command -l", shell=True)
    else:
        raise NotImplementedError(f"Lock not implemented for {system}")
    return jsonify({"message": "Locked"}), 200

def reboot():
    system = platform.system()
    
    if system == "Windows":
        os.system("shutdown /r /t 1")
    elif system == "Linux":
        os.system("reboot")
    else:
        raise NotImplementedError(f"Reboot not implemented for {system}")

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)
