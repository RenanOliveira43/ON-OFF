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

    commandHandler = {"turnOff":turn_off, "lock": lock, "reboot": reboot}

    if not cmd or cmd not in commandHandler:
        return jsonify({"error": "Missing command or invalid command"}), 400

    try:
        handler = commandHandler.get(cmd)
        return handler()
    except NotImplementedError as e:
        return jsonify({"error": str(e)}), 501

@app.route('/ping', methods=['GET'])
def ping():
    return jsonify({"status": "ok"}), 200

def turn_off():
    system = platform.system()
    if system == "Windows":
        subprocess.run("shutdown /s /t 1", shell=True)
    elif system == "Linux":
        subprocess.run("shutdown now", shell=True)
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
        subprocess.run("shutdown /r /t 1", shell=True)
    elif system == "Linux":
        subprocess.run("reboot", shell=True)
    else:
        raise NotImplementedError(f"Reboot not implemented for {system}")
    return jsonify({"message": "Rebooting..."}), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)
