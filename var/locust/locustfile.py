from locust import HttpLocust, TaskSet, task


class WebsiteUserBehavior(TaskSet):
    def on_start(self):
        pass

    @task
    def index(self):
        self.client.get("/jee7-sandbox/index.jsp")


class WebsiteUser(HttpLocust):
    task_set = WebsiteUserBehavior
    min_wait=5000
    max_wait=9000
