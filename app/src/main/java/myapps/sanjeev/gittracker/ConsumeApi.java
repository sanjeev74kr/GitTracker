package myapps.sanjeev.gittracker;

import androidx.annotation.NonNull;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConsumeApi {
    public interface Callback {
        void onSuccess(String responseBody);
        void onFailure(Exception e);
    }

    OkHttpClient okHttpClient = new OkHttpClient();

    public void fetchGithubRepo(String ownerName, String repoName, Callback callback) {
        String url = "https://api.github.com/repos/" + ownerName + "/" + repoName;

        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/vnd.github+json")
                .build();

        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();
                    callback.onSuccess(responseBody);
            }
        });
    }
}
