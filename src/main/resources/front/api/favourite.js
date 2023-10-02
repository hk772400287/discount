function deleteFavourite (params) {
    return $axios({
        url: '/favourite',
        method: 'delete',
        params
    })
}


function addFavourite (params) {
    return $axios({
        url: '/favourite',
        method: 'post',
        data: { ...params }
    })
}


function getFavouriteByCategory (params) {
    return $axios({
        url: '/favourite',
        method: 'get',
        params
    })
}