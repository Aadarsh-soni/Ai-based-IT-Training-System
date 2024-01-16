from flask import Flask, request, jsonify
import openai
from googleapiclient.discovery import build
import json

app = Flask(__name__)

# Set your OpenAI API key
openai.api_key = 'api_key'

# Set your YouTube API key
youtube_api_key = 'yt_api_key'  # Replace with your actual API key
youtube = build('youtube', 'v3', developerKey=youtube_api_key)

def Learning_Path(role, learning_topic, experience):
    prompt = f"Suggest Learning Path for a {role} who wants to learn {learning_topic} at {experience} level."
    response = openai.Completion.create(
        engine="text-davinci-003",
        prompt=prompt,
        temperature=0.7,
        max_tokens=500
    )
    return response['choices'][0]['text'].strip()

def generate_post_study_questions(course_content, experience,user_dee):
    prompt = f"Generate 5 Multiple choice technical questions with a correct based on the course content: {course_content,experience,user_dee}. and dont ask the question which can have the multiple correct option and also generate new question everytime. Generate it in application/json. use the question no in intger, options, and correct option as the key, at the end convert the whole output into json format"
    response = openai.Completion.create(
        engine="text-davinci-003",
        prompt=prompt,
        temperature=0.7,
        max_tokens=800
    )
    return response['choices'][0]['text'].strip()

def get_youtube_video_details(role, technology, experience):
    query = f"{technology} {role} tutorials {experience}"
    request = youtube.search().list(
        part='snippet',
        q=query,
        type='video',
        order='relevance',
        videoDuration='long',
        maxResults=5
    )
    response = request.execute()

    video_details = []
    for item in response.get('items', []):
        video_id = item['id']['videoId']
        video_title = item['snippet']['title']
        thumbnail_url = item['snippet']['thumbnails']['medium']['url']
        video_details.append({
            'videoId': video_id,
            'videoTitle': video_title,
            'thumbnailUrl': thumbnail_url,
            'videoUrl': f"https://www.youtube.com/watch?v={video_id}"
        })

    return video_details

def analyze_user_details(user_details):
    prompt = f"Analyze the following user details:\n{user_details}\nExtract important information such as technical background, expertise, and areas of less experience. Give the brief feedback to the user according to its details for improvement, how he can improve its skillsets, suggest some skills and much like that"

    response = openai.Completion.create(
        engine="text-davinci-003",
        prompt=prompt,
        temperature=0.7,
        max_tokens=800
    )

    analyzed_details = response['choices'][0]['text'].strip()
    return analyzed_details


# Flask API endpoints

@app.route('/analyze_user_details', methods=['POST'])
def analyze_user_details_endpoint():
    data = request.json
    details = data.get('user_details', '')

    user_details = analyze_user_details(details)
    return jsonify({"user_details": user_details})

@app.route('/generate_post_study_questions', methods=['POST'])
def generate_post_study_questions_endpoint():
    data = request.json
    course_content = data.get('technology', '')
    new_var = data.get('experience', '')
    user_de = data.get('user_details', '')
    user_dee = analyze_user_details(user_de)
    post_study_questions_text = generate_post_study_questions(course_content, new_var, user_dee)
    return post_study_questions_text


@app.route('/learning_path', methods=['POST'])
def learning_path_endpoint():
    data = request.json
    role = data.get('role', '')
    technology = data.get('technology', '')
    experience = data.get('experience', '')
    recommended_courses_text = Learning_Path(role, technology, experience)
    return jsonify({'learning_path': recommended_courses_text})

if __name__ == "__main__":
    app.run(port=3399, debug=True)

