package ir.sharif.simplenote.util

/**
 * A sealed class representing the result of an operation.
 *
 * @param T The type of data on success
 * @param E The type of structured error object
 */
sealed class Resource<out T, out E> {

    /**
     * Represents a successful operation with its data.
     * @property data The non-null data payload
     */
    data class Success<out T>(val data: T) : Resource<T, Nothing>()

    /**
     * Represents a failed operation where the error was successfully parsed into a structured error object.
     * @property error The non-null parsed error object of type E
     */
    data class Error<out E>(val error: E) : Resource<Nothing, E>()

    /**
     * Represents a generic failure that could not be parsed into the structured error type E.
     * This covers any kind of error: network errors, parsing errors, validation errors, etc.
     * @property message A descriptive error message
     * @property throwable The exception that occurred, useful for logging and debugging
     */
    data class Failure(
        val message: String?,
        val throwable: Throwable? = null
    ) : Resource<Nothing, Nothing>()

    // State check properties
    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
    val isFailure: Boolean get() = this is Failure

    /**
     * Returns the data if this is a Success, null otherwise.
     */
    fun getDataOrNull(): T? = if (this is Success) data else null

    /**
     * Returns the error if this is an Error, null otherwise.
     */
    fun getErrorOrNull(): E? = if (this is Error) error else null


    /**
     * Executes the given [block] if this Resource is a Success.
     * @param block The function to execute with the data
     * @return The original Resource instance for chaining
     */
    inline fun onSuccess(block: (T) -> Unit): Resource<T, E> {
        if (this is Success) {
            block(data)
        }
        return this
    }

    /**
     * Executes the given [block] if this Resource is an Error.
     * @param block The function to execute with the structured error
     * @return The original Resource instance for chaining
     */
    inline fun onError(block: (E) -> Unit): Resource<T, E> {
        if (this is Error) {
            block(error)
        }
        return this
    }

    /**
     * Executes the given [block] if this Resource is a Failure.
     * @param block The function to execute with failure details
     * @return The original Resource instance for chaining
     */
    inline fun onFailure(block: (message: String?, throwable: Throwable?) -> Unit): Resource<T, E> {
        if (this is Failure) {
            block(message, throwable)
        }
        return this
    }

    /**
     * Executes the given [block] if this Resource is either Error or Failure.
     * @param block The function to execute with error details
     * @return The original Resource instance for chaining
     */
    inline fun onAnyError(block: (error: E?, message: String?, throwable: Throwable?) -> Unit): Resource<T, E> {
        when (this) {
            is Error -> block(error, null, null)
            is Failure -> block(null, message, throwable)
            else -> Unit // Do nothing for Success
        }
        return this
    }

    /**
     * Transforms the data if this is a Success using the provided [transform] function.
     * @param transform The transformation function
     * @return A new Resource with the transformed data, or the original Resource if not Success
     */
    inline fun <R> map(transform: (T) -> R): Resource<R, E> {
        return when (this) {
            is Success -> Success(transform(data))
            is Error -> this
            is Failure -> this
        }
    }

    /**
     * Transforms the error if this is an Error using the provided [transform] function.
     * @param transform The transformation function
     * @return A new Resource with the transformed error, or the original Resource if not Error
     */
    inline fun <R> mapError(transform: (E) -> R): Resource<T, R> {
        return when (this) {
            is Success -> this
            is Error -> Error(transform(error))
            is Failure -> this
        }
    }

    companion object {
        /**
         * Creates a Success Resource with the given data.
         */
        fun <T> success(data: T): Resource<T, Nothing> = Success(data)

        /**
         * Creates an Error Resource with the given error.
         */
        fun <E> error(error: E): Resource<Nothing, E> = Error(error)

        /**
         * Creates a Failure Resource with the given parameters.
         */
        fun failure(
            message: String? = null,
            throwable: Throwable? = null
        ): Resource<Nothing, Nothing> = Failure(message, throwable)
    }
}
