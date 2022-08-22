package bruhcollective.itaysonlab.jetisoft.ext

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Query

internal suspend inline infix fun <T: Query.Data> ApolloClient.execute(query: Query<T>) = query(query).execute()