package com.abdlh.axelspringerhack.gettyConnectSDK.connectsdk.search;

import com.abdlh.axelspringerhack.gettyConnectSDK.connectsdk.SdkException;

public interface IBlendedImagesSearch {
    String ExecuteAsync() throws SdkException;

    IBlendedImagesSearch WithPage(int val);

    IBlendedImagesSearch WithPageSize(int val);

    IBlendedImagesSearch WithPhrase(String val);

    IBlendedImagesSearch WithSortOrder(String val);

    IBlendedImagesSearch WithEmbedContentOnly(boolean val);

    IBlendedImagesSearch WithExcludeNudity(boolean val);

    IBlendedImagesSearch WithResponseField(String val);

    IBlendedImagesSearch WithGraphicalStyle(GraphicalStyles val);

    IBlendedImagesSearch WithOrientation(Orientation val);

    IBlendedImagesSearch WithLicenseModel(LicenseModel val);

    ICreativeImagesSearch Creative();

    IEditorialImagesSearch Editorial();
}
