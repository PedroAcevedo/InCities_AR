//
//  MasGuideInfo.h
//  MaxstARSDKFramework
//
//  Created by Kimseunglee on 2018. 3. 21..
//  Copyright © 2018년 Maxst. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MasTagAnchor.h"

@interface MasGuideInfo : NSObject

- (instancetype)init:(void *)guideInfo;

/**
 * @brief Get a percentage of progress during an initialization step of SLAM
 * @return Slam initializing progress
 */
- (float)getInitializingProgress;

/**
 * @return keyframe count
 */
- (int)getKeyframeCount;

/**
 * Get the number of features for guide
 * @return feature point count
 */
- (int)getGuideFeatureCount;

/**
 * @return Get 2d screen positions of features for guide
 */
- (float *)getGuideFeatureBuffer;

/**
 * Get a bounding box of a scanned object
 * @return buffer including bounding box information
 */
- (float *)getBoundingBox;

/**
 * Get Anchors of a scanned object
 * @return buffer including Anchor information
 */
- (NSMutableArray<MasTagAnchor *> *)getTagAnchors;

/**
* Get number of anchors
* @return number of anchors
*/
- (int)getTagAnchorCount;
@end
