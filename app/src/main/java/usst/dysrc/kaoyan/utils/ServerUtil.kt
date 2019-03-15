package usst.dysrc.kaoyan.utils

object ServerUtil{
    const val SERVER_HOST_URL:String="http://192.168.1.109:8096/kaoyan"
    const val SERVER_USER_LOGIN_URL:String="/user/login"
    const val SERVER_USER_REGISTER_URL:String="/user/register"
    const val SERVER_USER_CHANGE_USER_INFO_URL:String="/user/updateUserInfo"
    const val SERVER_USER_GET_CLOCKED_LIST:String="/user/getClockedList"
    const val SERVER_USER_CREATE_NEW_CLOCKED="/user/createNewClocked"
    const val SERVER_POST_CREATE_POST_URL:String="/post/createPost"
    const val SERVER_POST_SEARCH_POST_URL:String="/post/searchPost"
    const val SERVER_POST_SEARCH_POST_DETAIL:String="/post/searchPostDetail"
    const val SERVER_POST_ADMIRE_URL:String="/post/admire"
    const val SERVER_POST_DELETE_POST_URL="/post/deletePost"
    const val SERVER_POST_INCREASE_COMMENT_URL="/post/increaseComment"
    const val SERVER_POST_COLLECT_POST_URL="/post/collectPost"
    const val SERVER_POST_SEARCH_COLLECTIONS_URL="/post/searchCollectionList"
}