package bruhcollective.itaysonlab.jetisoft.core.ext

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Query

suspend fun <T: Query.Data> Query<T>.awaitOn(client: ApolloClient): ApolloResponse<T> = client.query(this).execute()
