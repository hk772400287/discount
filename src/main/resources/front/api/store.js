function getAllStoresForUserByPage (params) {
    return $axios({
        url: '/store/userpage',
        method: 'get',
        params
    })
}


function getStoreById (id) {
    return $axios({
        url: '/store',
        method: 'get',
        params: { id }
    })
}


const commonDownload = (params) => {
    return $axios({
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        url: '/common/download',
        method: 'get',
        params
    })
}