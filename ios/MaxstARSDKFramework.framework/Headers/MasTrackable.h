//
//  MasTrackerable.h
//  MaxstAR
//
//  Created by Kimseunglee on 2017. 11. 24..
//  Copyright © 2017년 Maxst. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <simd/SIMD.h>

/**
 * @brief Container for individual tracking information
 */
@interface MasTrackable : NSObject
- (instancetype)init:(void*)trackable;

/**
 * @return 4x4 matrix for tracking pose
 */
- (matrix_float4x4) getPose;

/**
 * @return tracking target name name (file name without extension)
 */
- (NSString*) getName;

/**
 * @return tracking target id
 */
- (NSString*) getId;

/**
* @return tracking target Cloud Name (for use Cloud Recognition)
*/
- (NSString*) getCloudName;

/**
* @return tracking target width
*/
- (float) getWidth;

/**
* @return tracking target height
*/
- (float) getHeight;

/**
* @return tracking target Cloud MetaData (for use Cloud Recognition)
*/
- (NSString*) getCloudMetaData;
@end
