package me.hlatky.wbpo.client

import me.hlatky.wbpo.model.ApiError
import java.io.IOException

class ApiException(error: ApiError) : IOException(error.error)
